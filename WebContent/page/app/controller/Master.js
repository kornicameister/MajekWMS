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
    views : [
        'MasterView',
        'dialog.Warehouse',
        'dialog.WarehouseSelector'
    ],

    init: function () {
        console.init('WMS.controller.Master initializing...');
        var me = this,
            warehousesStore = me.getWarehousesStore(),
            warehouses = undefined;

        me.control({
            'wizardwarehouse'             : {
                show: function () {
                    console.log('Master:: WarehouseWizard is visible');
                }
            },
            '#submitButton'               : {
                click: me.onWarehouseSubmit
            },
            '#warehouseSelectorOpenButton': {
                click: me.onWarehouseSelected
            },
            'warehouseselector grid'      : {
                selectionchange: function (grid, selected) {
                    selected = selected[0];
                    console.log('Master:: Warehouse ' + Ext.String.format('{0} marked as active', selected.get('name')));
                    warehousesStore.setActive(selected);
                }
            }
        });

        me.getWarehousesStore().addListener('load', function (store) {
            warehouses = store.getWarehouses();
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
                console.log(Ext.String.format('Master:: Located warehouses at count [{0}]', warehouses.length));
                me.openWarehouseSelector();
            }
        });
    },

    onWarehouseSelected: function () {
        var me = this,
            warehouse = me.getWarehousesStore().getActive();
        warehouse.getUnits().load({
            callback: function (records) {
                console.log("Master :: Successfully loaded "
                    + records.length + ' records for Warehouse ['
                    + warehouse.get('name') + ']');
            }
        });
        me.warehouseselector.close();
    },

    /**
     * Method called after message box informing user of no warehouse
     * Opens wizards.
     */
    openWarehouseWizard: function () {
        var me = this;
        me.wizardwarehouse = me.getView('dialog.Warehouse').create();
        me.wizardwarehouse.show();
    },

    openWarehouseSelector: function () {
        var me = this;
        me.warehouseselector = me.getView('dialog.WarehouseSelector').create();
        me.warehouseselector.show();
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
    }
});