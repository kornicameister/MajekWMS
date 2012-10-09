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
    extend: 'Ext.app.Controller',

    stores: ['Warehouses'],
    // TODO add warehouse.selector
    views : ['MasterView', 'wizard.Warehouse'],

    init: function () {
        console.init('WMS.controller.Master initializing...');
        var me = this;

        me.control({
            'wizardwarehouse': {
                show: function () {
                    console.log('Master:: WarehouseWizard is visible')
                }
            },
            '#submitButton'  : {
                click: me.onWarehouseSubmit
            }
        });

        me.getWarehousesStore().addListener('load', function (store) {
            var warehouses = store.getWarehouses();
            if (warehouses.length === 0) {
                console.log("Master:: Found no warehouses, commencing loading warehouse wizard");

                Ext.MessageBox.show({
                    title        : 'No warehouse found...',
                    msg          : 'MajekWMS detected that there is no warehouse defined',
                    buttons      : Ext.MessageBox.OK,
                    fn           : me.openWarehouseWizard,
                    animateTarget: Ext.getBody(),
                    icon         : Ext.MessageBox.WARNING,
                    scope        : me
                });
            } else {
                console.log(String.format('Master:: Located warehouses at count [{0}]', warehouses.length));
                me.openWarehouseSelector(warehouses);
            }
        });
        me.getWarehousesStore().addListener('update', me.onWarehouseCreated, me);
    },

    onWarehouseCreated: function (store) {
        var warehouse = store.getActive(),
            me = this;
        Ext.MessageBox.show({
            title        : 'Warehouse created',
            msg          : 'MajekWMS created warehouse ' + warehouse.get('name'),
            buttons      : Ext.MessageBox.OK,
            animateTarget: Ext.getBody(),
            fn           : function () {
                me.wizardwarehouse.close();
            },
            icon         : Ext.MessageBox.INFO,
            scope        : this
        });
    },

    /**
     * Method called after message box informing user of no warehouse
     * Opens wizards.
     */
    openWarehouseWizard: function () {
        var me = this;

        Ext.getCmp('statusBar').setStatus({
            text : 'No warehouses found, opening Warehouse wizard',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });

        me.wizardwarehouse = me.getView('wizard.Warehouse').create();
        me.wizardwarehouse.show();
    },

    openWarehouseSelector: function (warehouses) {
//        Ext.getCmp('statusBar').setStatus({
//            text : 'Warehouse ' + store.getLastWarehouse().get('name') + ' up and running...',
//            clear: {
//                wait       : 10000,
//                anim       : true,
//                useDefaults: false
//            }
//        });
    },

    /**
     * Method called in response to click
     * on submit button, that commence chain
     * reaction that results in
     * creating new warehouse
     * @param button
     */
    onWarehouseSubmit: function (button) {
        var form = button.up('form').getForm(),
            me = this;

        Ext.getCmp('statusBar').showBusy();
        if (form.isValid()) {
            var w = me
                .getWarehousesStore()
                .addWarehouse(form.getValues())[0];
            if (Ext.isDefined(w)) {
                me.getWarehousesStore().setActive(
                    w.getId() ? w.getId() : w
                );
            }
        }
        Ext.getCmp('statusBar').clearStatus();
    }
});