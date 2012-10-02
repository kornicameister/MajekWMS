/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

Ext.define('WMS.controller.WarehouseWizard', {
    extend: 'Ext.app.Controller',

    stores: ['SimpleUnitTypes', 'Warehouses'],

    init: function () {
        console.init('WMS.controller.Configuration initializing...');
        var me = this;

        me.getWarehousesStore().addListener('load', function (confStore) {
            var warehouses = confStore.getWarehouses();
            if (warehouses.length === 0) {
                Ext.MessageBox.show({
                    title        : 'No warehouse found...',
                    msg          : 'MajekWMS detected that' +
                        ' there is no warehouse defined\n' +
                        'Would you like to create one now ?',
                    buttons      : Ext.MessageBox.YESNO,
                    fn           : me.openWarehouseWizard,
                    animateTarget: Ext.getBody(),
                    icon         : Ext.MessageBox.QUESTION,
                    scope        : me
                });
            } else {
                me.openWarehouseSelector();
            }
        });
        me.getWarehousesStore().addListener('update', me.onConfigurationUpdate, me);
    },

    onConfigurationUpdate: function (store) {
        var warehouse = store.getWarehouses()[0];
        Ext.MessageBox.show({
            title        : 'Warehouse created',
            msg          : 'MajekWMS created warehouse ' + warehouse['name'],
            buttons      : Ext.MessageBox.OK,
            animateTarget: Ext.getBody(),
            fn           : function () {
                this.wizard.close();
            },
            icon         : Ext.MessageBox.INFO,
            scope        : this
        });
    },

    /**
     * Method called after message box informing user of no warehouse
     * if closed either with Yes or No button
     * @param answer
     */
    openWarehouseWizard: function (answer) {
        var me = this;
        if (answer === 'yes') {
            me.wizard = Ext.create('WMS.view.wizard.Warehouse');
            var submitButton = Ext.getCmp('warehouseSubmitButton');
            submitButton.on('click', me.onWarehouseSubmit, me);
            me.wizard.show();
        }
    },

    /**
     * Method called if MajekWMS found valid warehouse.
     * User is allowed to choose one of them to operate on.
     */
    openWarehouseSelector: function () {
        var me = this;
    },

    onWarehouseSubmit: function (button) {
        var form = button.up('form').getForm(),
            me = this;
        if (form.isValid()) {
            me.getWarehousesStore().addWarehouse(form.getValues());
        }
    }
});