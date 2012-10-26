/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 17:02
 */

Ext.define('WMS.controller.Login', {
    extend: 'Ext.app.Controller',

    requires: [
        'WMS.view.login.Dialog',
        'WMS.view.login.Form',
        'WMS.view.wizard.Warehouse'
    ],
    views   : [
        'login.Dialog'
    ],
    stores  : [
        'Warehouses'
    ],
    refs    : [
        {
            ref     : 'dialog',
            selector: 'logindialog'
        },
        {
            ref     : 'loginForm',
            selector: 'logindialog loginform'
        },
        {
            ref     : 'warehousesCombo',
            selector: 'logindialog panel combo'
        }
    ],

    init: function () {
        console.init('WMS.controller.Login initializing...');
        var me = this;

        me.control({
            'logindialog'                             : {
                'render': function () {
                    console.log('Lol I have been rendered');
                }
            },
            'logindialog loginform button[text=Login]': {
                'click': me.onLoginButtonClicked
            },
            'logindialog panel combo'                 : {
                'select': me.onWarehouseSelected
            },
            'logindialog openWarehouseButton'         : {
                'click': me.onWarehouseOpen
            }
        });
        me.mon(me.getWarehousesStore(), 'load', me.onWarehousesLoad, me);
    },

    onWarehousesLoad: function (store) {
        console.log('Login :: Warehouses store has been loaded');
        var me = this,
            warehouses = store.getRange(),
            warehouseCombo = me.getWarehousesCombo();

        if (warehouses.length === 0) {
            console.log("Login :: Found no warehouses, commencing loading warehouse wizard");
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
            console.log(Ext.String.format('Login :: Located warehouses at count [{0}]', warehouses.length));
            warehouseCombo.reconfigure(warehouses);
        }
    },

    onWarehouseSelected: function (combo, selected) {
        var me = this,
            warehousesStore = me.getWarehousesStore();

        selected = selected[0];
        console.log('Login :: Warehouse ' + Ext.String.format('{0} marked as active', selected.get('name')));
        warehousesStore.setActive(selected);
    },

    onWarehouseOpen: function () {
        var me = this,
            warehouse = me.getWarehousesStore().getActive();

        warehouse.getUnits().load({
            callback: function (records) {
                console.log("Login :: Successfully loaded "
                    + records.length + ' records for Warehouse ['
                    + warehouse.get('name') + ']');
                me.getController('WMS.controller.Toolbars').getUnitMenu().reconfigure(warehouse.getUnits());
            }
        });
    },

    openWarehouseWizard: function () {
        var me = this,
            wizardCtrl = me.getController('WMS.controller.wizard.Warehouse');

        wizardCtrl.openWizard();
    },

    onLoginButtonClicked: function (button) {
        console.log('Login :: Login button has been clicked...');
        var me = this,
            warehouses = me.getWarehousesStore(),
            formRef = button.up('form');

        formRef.submit({
            method      : 'POST',
            jsonSubmit  : true,
            paramsAsHash: true,
            waitTitle   : 'Connecting',
            waitMsg     : 'Sending data...',

            success: function (form, action) {
                var obj = Ext.util.JSON.decode(action.response.responseText);
                Ext.Msg.alert('Status', 'Login Successful!', function (btn) {
                    if (btn == 'ok') {
                        warehouses.load()
                    }
                });
            },

            failure: function (form, action) {
                if (action.failureType == 'server') {
                    var obj = Ext.util.JSON.decode(action.response.responseText);
                    Ext.Msg.alert('Login Failed!', obj.errors.reason);
                } else {
                    Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText);
                }
                formRef.reset();
            }
        });
    }
});