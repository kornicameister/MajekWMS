/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 17:02
 */

/**
 * @class WMS.controller.Login
 *
 */
Ext.define('WMS.controller.Login', {
    extend                  : 'Ext.app.Controller',
    views                   : [
        'login.Dialog'
    ],
    stores                  : [
        'Companies'
    ],
    refs                    : [
        { ref: 'dialog', selector: 'logindialog' },
        { ref: 'loginForm', selector: 'logindialog loginform' }
    ],
    config                  : {
        loadMask: undefined
    },
    init                    : function () {
        console.init('WMS.controller.Login initializing...');
        var me = this;

        me.control({
            'logindialog loginform button[text=Login]': {
                'click': me.onLoginButtonClicked
            },
            '#viewport'                               : {
                'render': me.maskViewport
            }
        });
    },
    maskViewport            : function (view) {
        if (Ext.isDefined(view)) {
            var me = this;
            me.setLoadMask(new Ext.LoadMask(view.el, { msg: 'Loading content...'}));
            me.getLoadMask().show();
        } else {
            console.log('Login :: Can not mask viewport, view undefined');
        }
    },
    onLoginButtonClicked    : function (button) {
        console.log('Login :: Login button has been clicked...');
        var me = this,
            formRef = button.up('form');

        formRef.submit({
            method      : 'POST',
            jsonSubmit  : true,
            paramsAsHash: true,
            waitTitle   : 'Connecting',
            waitMsg     : 'Sending data...',

            success: function (form, action) {
                var obj = Ext.decode(action.response.responseText);

                if (obj['success'] === true) {
                    var user = obj['data'][0];
                    Ext.getCmp('statusBar').setStatus({
                        text : Ext.String.format('Zalogowałeś się jako {0}', user['login']),
                        clear: {
                            wait       : 10000,
                            anim       : true,
                            useDefaults: false
                        }
                    });
                    me.closeLoginDialog();
                    me.checkCompanies();
                    me.saveInformationToSession(user);
                } else {
                    Ext.Msg.alert('Logowanie nieudane',
                        'Podany login lub hasło są błędne !!!'
                    );
                    formRef.reset();
                }
            },

            failure: function (form, action) {
                var obj = Ext.decode(action.response.responseText),
                    success = obj['success'];
                if (action.failureType == 'server') {
                    Ext.Msg.alert('Logowanie nieudane :: ', (!success ? 'Złe dane logowanie' : 'brak danych'));
                } else {
                    Ext.Msg.alert('Logowanie nieudane',
                        'Serwer jest obecnie niedostępny : ' + action.response.responseText);
                }
                formRef.reset();
            }
        });
    },
    getUserFromSesssion     : function () {
        var session = Ext.state.Manager,
            user = session.get('user', undefined);
        return user;
    },
    saveInformationToSession: function (user) {
        var session = Ext.state.Manager;
        session.set("user", user);
    },
    checkCompanies          : function () {
        var me = this,
            companies = me.getCompaniesStore();

        companies.load({
            callback: function (data) {
                if (Ext.isArray(data) && data.length >= 1) {
                    console.log('Login :: ' +
                        Ext.String.format('Located {0} compan{1}', data.length, (data.length === 1 ? 'y' : 'ies')));
                    Ext.MessageBox.show({
                        title  : 'Zalogowany',
                        msg    : Ext.String.format('Rozpoczynam pracę, firma {0}', data[0].get('longName')),
                        width  : 200,
                        buttons: Ext.Msg.OK,
                        icon   : Ext.window.MessageBox.INFO
                    });
                } else {
                    console.log('Login :: No suitable company found, commencing wizard');
                    me.openCompanyWizard();
                }
            }
        });
        me.getLoadMask().hide();
    },
    openCompanyWizard       : function () {
        var me = this,
            companyWizardCtrl = me.getController('WMS.controller.wizard.Company');

        if (Ext.isDefined(companyWizardCtrl)) {
            companyWizardCtrl.openWizard();
        }
    },
    openLoginDialog         : function () {
        var me = this,
            loginDialog = me.getView('WMS.view.login.Dialog');
        loginDialog.create().show();
    },
    closeLoginDialog        : function () {
        var me = this,
            loginDialog = me.getDialog();
        loginDialog.close();
    }
});
