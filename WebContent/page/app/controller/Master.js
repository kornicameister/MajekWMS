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
    views               : [
        'Master'
    ],
    refs                : [
        {  ref: 'masterView', selector: 'masterview' }
    ],
    init                : function () {
        console.init('WMS.controller.Master initializing...');
        var me = this;
        me.control({
            'masterview': {
                'afterrender': me.onMasterRender
            }
        });
    },
    onMasterRender      : function (view) {
        console.log('Master :: Opening login.Dialog...');
        var me = this,
            loginController = me.getController('WMS.controller.Login');

        if (Ext.isDefined(loginController)) {
            loginController.openLoginDialog();
        }
    },
    openRecipientManager: function () {
        console.log('Master :: Opening recipients manager');
        var me = this;
        me.openManager('manager.recipient.Manager');
    },
    openSupplierManager : function () {
        console.log('Master :: Opening suppliers manager');
        var me = this;
        me.openManager('manager.supplier.Manager');
    },
    //  --- private --- ... //
    /**
     * @private
     * @description Open desired manager
     * @param view
     */
    openManager         : function (view) {
        var me = this,
            masterView = me.getMasterView(),
            manager = me.getView(view).create();

        manager = masterView.add(manager);
        manager.show();
    }
});