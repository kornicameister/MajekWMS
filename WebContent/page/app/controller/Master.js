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
        var me = this;

        me.control({
            '#submitButton'               : {
                click: me.onWarehouseSubmit
            },
            '#warehouseSelectorOpenButton': {
                click: me.onWarehouseSelected
            },
            'warehouseselector grid'      : {
                selectionchange: me.onWarehouseSelectionChange
            }
        });

        me.mon(me.getWarehousesStore(), 'load', me.onWarehousesLoad, me);
    },

    onWarehousesLoad: function (store) {
        var me = this,
            warehouses = store.getRange();

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
//            me.openWarehouseSelector();
        }
    },

    onWarehouseSelectionChange: function (grid, selected) {
        var me = this,
            warehousesStore = me.getWarehousesStore();

        selected = selected[0];
        console.log('Master:: Warehouse ' + Ext.String.format('{0} marked as active', selected.get('name')));
        warehousesStore.setActive(selected);
    },

    onWarehouseSelected: function () {
        var me = this,
            warehouse = me.getWarehousesStore().getActive();

        warehouse.getUnits().load({
            callback: function (records) {
                console.log("Master :: Successfully loaded "
                    + records.length + ' records for Warehouse ['
                    + warehouse.get('name') + ']');
                me.getController('WMS.controller.Toolbars').getUnitMenu().reconfigure(warehouse.getUnits());
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
        me.openLogin();
    },

    openLogin: function () {
        var me = this;
        me.login = me.getView('dialog.Login').create();
        me.login.show();
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