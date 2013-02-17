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
    extend              : 'Ext.app.Controller',
    views               : [
        'login.Dialog'
    ],
    stores              : [
        'Companies'
    ],
    refs                : [
        { ref: 'dialog', selector: 'logindialog' },
        { ref: 'loginForm', selector: 'logindialog loginform' }
    ],
    init                : function () {
        console.init('WMS.controller.Login initializing...');
        var me = this;

        me.control({
            'logindialog loginform button[text=Login]': {
                'click': me.onLoginButtonClicked
            }
        });
    },
    onLoginButtonClicked: function (button) {
        console.log('Login :: Login button has been clicked...');
        var me = this,
            formRef = button.up('form'),
            masterController = me.getController('WMS.controller.Master'),
            toolbarController = me.getController('WMS.controller.Toolbars');

        formRef.submit({
            method      : 'POST',
            jsonSubmit  : true,
            paramsAsHash: true,
            baseParams  : {
                action: 'login'
            },
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
                    toolbarController.setLoggedUserInformation(user);
                    masterController.unmaskViewport(masterController);
                } else {
                    Ext.Msg.alert('Logowanie nieudane', 'Podany login lub hasło są błędne !!!');
                    formRef.reset();
                }
            },

            failure: function (form, action) {
                var obj = Ext.decode(action.response.responseText), success = obj['success'];
                if (action.failureType == 'server') {
                    Ext.Msg.alert('Logowanie nieudane :: ', (!success ? 'Złe dane logowanie' : 'brak danych'));
                } else {
                    Ext.Msg.alert('Logowanie nieudane', 'Serwer jest obecnie niedostępny : ' + action.response.responseText);
                }
                formRef.reset();
            }
        });
    },
    checkCompanies      : function () {
        var me = this,
            companies = me.getCompaniesStore();

        companies.load({
            callback: function (data) {
                if (Ext.isArray(data) && data.length >= 1) {
                    console.log('Login :: ' + Ext.String.format('Located {0} compan{1}', data.length, (data.length === 1
                        ? 'y' : 'ies')));
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
    },
    openCompanyWizard   : function () {
        var me = this, companyWizardCtrl = me.getController('WMS.controller.wizard.Company');

        if (Ext.isDefined(companyWizardCtrl)) {
            companyWizardCtrl.openWizard();
        }
    },
    openLoginDialog     : function () {
        var me = this, loginDialog = me.getView('WMS.view.login.Dialog');
        loginDialog.create().show();
    },
    closeLoginDialog    : function () {
        var me = this, loginDialog = me.getDialog();
        loginDialog.close();
    },
    logout              : function () {
        var me = this,
            responseObj = undefined,
            toolbarController = me.getController('WMS.controller.Toolbars');

        Ext.Ajax.request({
            url    : 'wms/auth/logout',
            method : 'POST',
            timeout: 2000,
            success: function (response) {
                responseObj = Ext.decode(response.responseText);
                if (responseObj['success'] === true) {
                    console.log('LOGIN :: User has been removed from the session');

                    // removing content from logger user info
                    // and reload application
                    toolbarController.clearLoggerUserInformation();
                    window.location = "/MajekWMS"
                }
            },
            failure: function () {
                console.log('unimplemented');
            }
        })
    },
    getUserFromSession  : function (callback, scope) {
        console.log('LOGIN :: Retrieving user from the session in progress...');
        var user = false;
        Ext.Ajax.request({
            url    : 'wms/auth',
            method : 'GET',
            timeout: 2000,
            success: function (response) {
                var obj = Ext.decode(response.responseText);
                if (obj['success'] === true) {
                    user = obj['data'][0];
                    console.log('LOGIN :: Valid user instance found in the session');
                } else {
                    console.log('LOGIN :: User not found in session')
                }
                callback.apply(scope, [user]);
            },
            failure: function () {
                console.log('unimplemented');
            }
        });
    }
});
