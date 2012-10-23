/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:44
 */

Ext.define('WMS.controller.wms.Unit', {
    extend: 'Ext.app.Controller',
    views : [
        'WMS.view.wms.Unit'
    ],
    refs  : [
        {
            ref     : 'unitPanel',
            selector: 'wmsunit'
        }
    ],

    init: function () {
        console.init('WMS.controller.wms.Unit initializing');
    },

    activatePlacement: function () {
        var me = this,
            unitPanel = me.getUnitPanel();

        unitPanel['items'].get('wmsUnitSchema').expand();
    },

    activateInventory: function () {
        var me = this,
            unitPanel = me.getUnitPanel();

        unitPanel['items'].get('wmsInventory').expand();
    }
});