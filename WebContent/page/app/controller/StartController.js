/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

Ext.define('WMS.controller.StartController', {
    extend: 'Ext.app.Controller',

    stores: ['Warehouses'],
    views : ['wizard.Warehouse'],

    init: function () {
        console.init('WMS.controller.StartController initializing...');
        var me = this;

        me.control({
            'wizardwarehouse': {
                show: function () {
                    console.log('StartController:: WarehouseWizard is visible')
                }
            },
            '#submitButton'  : {
                click: me.onWarehouseSubmit
            }
        });

        me.getWarehousesStore().addListener('load', function (store) {
            if (store.getWarehouses().length === 0) {
                console.log("StartController:: Found no warehouses, commencing loading warehouse wizard");

                Ext.MessageBox.show({
                    title        : 'No warehouse found...',
                    msg          : 'MajekWMS detected that there is no warehouse defined',
                    buttons      : Ext.MessageBox.OK,
                    fn           : me.openWarehouseWizard,
                    animateTarget: Ext.getBody(),
                    icon         : Ext.MessageBox.WARNING,
                    scope        : me
                });
            }
        });
        me.getWarehousesStore().addListener('update', me.onWarehouseCreated, me);
    },

    onWarehouseCreated: function (store) {
        var warehouse = store.getLastWarehouse(),
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

    onWarehouseSubmit: function (button) {
        var form = button.up('form').getForm(),
            me = this;

        Ext.getCmp('statusBar').showBusy();
        if (form.isValid()) {
            console.log('StartController:: Added ', me.getWarehousesStore().addWarehouse(form.getValues()));
        }
        Ext.getCmp('statusBar').clearStatus();
    }
});