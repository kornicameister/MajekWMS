/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:42
 */

Ext.define('WMS.controller.wms.Overview', {
    extend: 'Ext.app.Controller',

    stores: [
        'UnitTypes'
    ],
    views : [
        'wms.Overview'
    ],
    refs  : [
        {ref: 'unitsGrid', selector: 'wmsoverviews grid'},
        {ref: 'warehouseDescription', selector: 'wmsoverviews panel'}
    ],

    gridLoadingMask: undefined,

    init: function () {
        console.init('WMS.controller.wms.Overview initializing...');
        var me = this,
            warehouses = me.getController('Master').getWarehousesStore();

        me.control({
            'wmsoverviews #add'      : {
                click: me.onNewUnit
            },
            'wmsoverviews #delete'   : {
                click: me.onUnitDelete
            },
            'wmsoverviews #unitsGrid': {
                selectionchange: me.onUnitSelectionChanged,
                afterrender    : me.onUnitGridAfterRender
            }
        });

        me.mon(warehouses, 'activechanged', me.onActiveWarehouseChange, me);
    },

    onActiveWarehouseChange: function (store, activeWarehouse) {
        var me = this,
            wd = me.getWarehouseDescription();

        if (Ext.isDefined(wd)) {
            console.log('Overview:: Active warehouse changed, switching to ' + activeWarehouse.get('name'));

            me.mon(
                activeWarehouse,
                'load',
                function (store) {
                    console.log('Overview :: Units`s changed, refreshing the unit\'s grid');
                    me.getUnitsGrid().reconfigure(store);
                    wd.update(activeWarehouse.getData());
                    me.gridLoadingMask.hide();
                });
            me.mon(
                activeWarehouse.getUnits(),
                'update',
                function (store, records) {
                    Ext.getCmp('statusBar').setStatus({
                        text : 'You\'ve successfully saved ' + records.length + ' units...',
                        clear: {
                            wait       : 10000,
                            anim       : true,
                            useDefaults: false
                        }
                    });
                });
        }
    },

    onUnitGridAfterRender: function (grid) {
        var me = this;
        me.gridLoadingMask = new Ext.LoadMask(grid, { msg: 'Loading content...'});
        me.gridLoadingMask.show();
    },

    onUnitSelectionChanged: function (selModel, selections) {
        var grid = this.getUnitsGrid();
        grid.down('#delete').setDisabled(selections.length === 0);
    },

    onNewUnit: function () {
        var me = this,
            store = me.getStore('Warehouses').getActive().getUnits(),
            grid = me.getUnitsGrid(),
            record = store.add(Ext.create('WMS.model.entity.Unit'));

        if (!Ext.isDefined(record)) {
            console.error('Overview :: Failed to add new unit when row edition enabled');
        }

        grid.getPlugin('unitRowEditor').startEdit(store.getTotalCount(), store.getTotalCount());

        Ext.getCmp('statusBar').setStatus({
            text : 'You\'ve just added new unit...',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },

    onUnitDelete: function () {
        var me = this,
            store = me.getStore('Warehouses').getActive().getUnits(),
            grid = me.getUnitsGrid(),
            selection = grid.getView().getSelectionModel().getSelection();

        if (selection) {
            store.remove(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('You\'ve just deleted {0} {1}',
                    selection.length,
                    selection.length > 0 ? 'units' : 'unit'),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    }
});