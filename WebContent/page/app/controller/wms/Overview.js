/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:42
 */

Ext.define('WMS.controller.wms.Overview', {
    extend: 'Ext.app.Controller',

    stores: ['Units'],
    views : ['wizard.Unit', 'wms.Overview'],

    init: function () {
        console.init('WMS.controller.wms.Overview initializing...');
        var me = this;

        me.control({
            '#submitUnit': {
                click: me.onUnitSubmit
            }
        });
    },

    onUnitSubmit: function (button) {
        var form = button.up('form').getForm(),
            me = this,
            unit = undefined;

        Ext.getCmp('statusBar').showBusy();
        if (form.isValid()) {
            me.getStore('Warehouses').addUnit(form.getValues());
        }
        Ext.getCmp('statusBar').clearStatus();
    }
});