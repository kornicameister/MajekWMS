/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

// TODO consider getting it fuck away !
Ext.define('WMS.controller.Master', {
    extend: 'Ext.app.Controller',

    requires: [
        'WMS.view.manager.client.Manager'
    ],

    views: [
        'WMS.view.Master',
        'WMS.view.manager.client.Manager'
    ],

    refs: [
        {
            ref     : 'masterView',
            selector: 'masterview'
        }
    ],

    init: function () {
        console.init('WMS.controller.Master initializing...');
        var me = this;
        me.control({
            'masterview': {
                'afterrender': me.onMasterRender
            }
        });
    },

    onMasterRender: function (view) {
        console.log('Master :: Opening login.Dialog...');
        Ext.create('WMS.view.login.Dialog');
    },

    openClientsManager: function () {
        console.log('Master :: Opening clients manager')
        var me = this,
            masterView = me.getMasterView(),
            clientsManager = me.getView('manager.client.Manager').create();

        clientsManager = masterView.add(clientsManager);
        clientsManager.show();
    }
});