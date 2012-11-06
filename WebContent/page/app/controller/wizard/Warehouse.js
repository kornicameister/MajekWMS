/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 25.10.12
 * Time   : 14:59
 */

Ext.define('WMS.controller.wizard.Warehouse', {
    extend           : 'Ext.app.Controller',
    views            : [
        'wizard.Warehouse'
    ],
    refs             : [
        {
            ref     : 'wizard',
            selector: 'wizardwarehouse'
        }
    ],
    stores           : [
        'Warehouses'
    ],
    init             : function () {
        console.init('WMS.controller.wizard.Warehouse initializing...');
        var me = this;

        me.control({
            'wizardwarehouse submitButton': {
                click: me.onWarehouseSubmit
            }
        });
    },
    openWizard       : function () {
        var me = this,
            wizardView = me.getWizard();

        wizardView.show(true);
    },
    onWarehouseSubmit: function (button) {
        var form = button.up('form').getForm(),
            me = this;

        if (form.isValid()) {
            var w = me
                .getWarehousesStore()
                .addWarehouse(form.getValues())[0];
            if (Ext.isDefined(w)) {
                me.getWarehousesStore().setActive(
                    w.getId() ? w.getId() : w
                );
            }
            console.log('wizard.Warehouse :: Warehouse created...');
            me.getWizard().close();
        }
    }
});