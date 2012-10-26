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
            selector: 'logindialog loginform combo'
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
            wCount = store.getRange().length;

        if (wCount === 0) {
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
            warehouse = me.getWarehousesStore().getActive(),
            unitMenu = me.getController('WMS.controller.Toolbars').getUnitMenu();

        warehouse.getUnits().load({
            callback: function () {
                unitMenu.reconfigure(this);
                me.getDialog().close();
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
            formRef = button.up('form');

        formRef.submit({
            method      : 'POST',
            jsonSubmit  : true,
            paramsAsHash: true,
            waitTitle   : 'Connecting',
            waitMsg     : 'Sending data...',

            success: function (form, action) {
                var obj = Ext.decode(action.response.responseText);

                if (obj['success'] === true) {
                    Ext.getCmp('statusBar').setStatus({
                        text : Ext.String.format('Zalogowałeś się jako {0}', obj['user']['login']),
                        clear: {
                            wait       : 10000,
                            anim       : true,
                            useDefaults: false
                        }
                    });
                    me.onWarehouseOpen();
                } else {
                    Ext.Msg.alert('Logowanie nieudane',
                        'Podany login lub hasło są błędne !!!'
                    );
                    formRef.reset();
                }
            },

            failure: function (form, action) {
                var obj = Ext.decode(action.response.responseText),
                    success = obj['success'];
                if (action.failureType == 'server') {
                    Ext.Msg.alert('Logowanie nieudane :: ', (!success ? 'Złe dane logowanie' : 'brak danych'));
                } else {
                    Ext.Msg.alert('Logowanie nieudane',
                        'Serwer jest obecnie niedostępny : ' + action.response.responseText);
                }
                formRef.reset();
            }
        });
    }
});