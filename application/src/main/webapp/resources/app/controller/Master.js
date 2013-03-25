/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

Ext.define('WMS.controller.Master', {
    extend              : 'Ext.app.Controller',
    uses                : [
        'WMS.view.manager.recipient.Manager', 'WMS.view.manager.supplier.Manager'
    ],
    refs                : [
        {  ref: 'masterView', selector: 'masterview' }
    ],
    config              : {
        tabs    : [],
        loadMask: undefined
    },
    init                : function () {
        console.init('WMS.controller.Master initializing...');
        var me = this;
        me.control({
            '#viewport': {
                'afterrender': me.onMasterRender
            }
        });
        me.tabs = [];
    },
    onMasterRender      : function (view) {
        console.log('Master :: Opening login.Dialog...');
        var me = this,
            loginController = me.getController('WMS.controller.Login'),
            toolbarController = me.getController('WMS.controller.Toolbars');

        function handleResponseFromSession(user) {
            if (user === false) {
                loginController.openLoginDialog();
            } else {
                toolbarController.setLoggedUserInformation(user);
                loginController.checkCompanies();
                me.unmaskViewport();
            }
        }

        me.maskViewport(view);
        loginController.getUserFromSession(handleResponseFromSession, me);
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
    openRecipientManager: function () {
        console.log('Master :: Opening recipients manager');
        var me = this;
        me.openManager('WMS.view.manager.recipient.Manager');
    },
    openSupplierManager : function () {
        console.log('Master :: Opening suppliers manager');
        var me = this;
        me.openManager('WMS.view.manager.supplier.Manager');
    },
    //  --- private --- ... //
    /**
     * @private
     * @description Open desired manager
     * @param view
     */
    openManager         : function (view) {
        var me = this, masterView = me.getMasterView(), manager = undefined, tabs = me.getTabs();

        if (Ext.Array.indexOf(tabs, view) < 0) {

            manager = me.getView(view).create();
            manager = masterView.add(manager);

            me.mon(manager, 'close', me.onTabClose, me);
            tabs.push(view);

            manager.show();
        }
    },
    onTabClose          : function (panel) {
        var me = this, tabs = me.getTabs(), className = Ext.ClassManager.getName(panel), index = Ext.Array.indexOf(tabs, className);

        if (index >= 0) {
            me.mun(panel, 'close', me.onTabClose);
            Ext.Array.erase(tabs, index, 1);
        } else {
            Ext.Error.raise({
                msg: 'Closing event was caught, but panel was not found in master tabs array'
            });
        }
    }
});
