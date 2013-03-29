/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:42
 */

Ext.define('WMS.controller.wms.Overview', function () {
    var wTemplate = new Ext.XTemplate(
            '<div class="warehouse-details">',
            '<div class="basic">',
            '<p>Magazyn: <b>{name}</b></p>',
            '<p>Opis: <b>{description}</b></p>',
            '</div>',
            '<div class="extended">',
            '<p>Rozmiar: <b>{size}</b></p>',
            '<p>Wykorzystanie: <b>{usage}%</b></p>',
            '</div>',
            '<div class="image-placeholder"/>',
            '</div>'
        ).compile(),
        applyWTemplate = function (target, data) {
            wTemplate.overwrite(target.body, data.getData());
        },
        loadWarehouseStore = function (store, view, reload, scope) {
            var mask = new Ext.LoadMask(view, {
                    msg  : 'Ładowanie danych, proszę czekać',
                    store: store
                }),
                options = {
                    scope: scope
                },
                task = new Ext.util.DelayedTask(function () {
                    if (reload) {
                        store.reload(options);
                    } else {
                        store.load(options);
                    }
                });
            task.delay(300);
        },
        loadUnitStore = function (store, warehouse_id, view, reload, scope) {
            var mask = new Ext.LoadMask(view, {
                    msg  : 'Ładowanie stref, proszę czekać',
                    store: store
                }),
                options = {
                    scope   : scope,
                    filters : [
                        {
                            value   : warehouse_id,
                            property: 'warehouse_id'
                        }
                    ],
                    callback: function (records) {
                        var length = records.length;
                        Ext.getCmp('statusBar').setStatus({
                            text : Ext.String.format('Uaktualniłeś {0} {1}', length, (length === 1 ? 'strefę' : 'stref')),
                            clear: {
                                wait       : 10000,
                                anim       : true,
                                useDefaults: false
                            }
                        });
                    }
                },
                task = new Ext.util.DelayedTask(function () {
                    if (reload) {
                        store.reload(options);
                    } else {
                        store.load(options);
                    }
                });
            task.delay(300);
        };
    return {
        extend                 : 'Ext.app.Controller',
        stores                 : [
            'UnitTypes',
            'Companies',
            'Warehouses',
            'Units'
        ],
        views                  : [
            'wms.Overview'
        ],
        refs                   : [
            {ref: 'overview', selector: 'wmsoverviews'},
            {ref: 'unitsGrid', selector: 'wmsoverviews egrid[itemId=unitsGrid]'},
            {ref: 'warehouseDescription', selector: 'wmsoverviews panel[itemId=description]'}
        ],
        init                   : function () {
            console.init('WMS.controller.wms.Overview initializing...');
            var me = this,
                companies = me.getCompaniesStore(),
                warehouses = me.getWarehousesStore(),
                units = me.getUnitsStore();

            me.control({
                'wmsoverviews #add'      : {
                    click: me.onNewUnit
                },
                'wmsoverviews #delete'   : {
                    click: me.onUnitDelete
                },
                'wmsoverviews #unitsGrid': {
                    selectionchange: me.onUnitSelectionChanged
                }
            });

            me.mon(warehouses, 'activechanged', me.onActiveWarehouseChange, me);
            me.mon(companies, 'load', me.onCompaniesLoad, me);
            me.mon(units, 'update', me.onCompaniesLoad, me);
        },
        onCompaniesLoad        : function () {
            var me = this,
                companies = me.getCompaniesStore(),
                warehouses = me.getWarehousesStore();

            var company = companies.getAt(0),
                warehouse = company.getWarehouse();

            console.log('Overview:: Warehouse found w=[', warehouse, ']');
            loadWarehouseStore(warehouses, me.getOverview(), false, me);
        },
        onActiveWarehouseChange: function (store, activeWarehouse) {
            var me = this,
                units = me.getUnitsStore();

            console.log('Overview:: Active warehouse changed, switching to ' + activeWarehouse.get('name'));
            me.mon(units, 'load', me.onUnitsStoreLoaded, me);
            loadUnitStore(units, activeWarehouse.getId(), me.getUnitsGrid(), false, me);
        },
        onUnitsStoreLoaded     : function (store) {
            var me = this,
                wd = me.getWarehouseDescription(),
                activeWarehouse = me.getWarehousesStore().getActive();

            console.log('Overview :: Units`s changed, refreshing the unit\'s grid');

            me.getUnitsGrid().reconfigure(store);
            applyWTemplate(wd, activeWarehouse);
        },
        onUnitSelectionChanged : function (selModel, selections) {
            var grid = this.getUnitsGrid();
            grid.down('#delete').setDisabled(selections.length === 0);
        },
        onNewUnit              : function () {
            var me = this,
                warehouses = me.getWarehousesStore(),
                store = me.getUnitsStore(),
                grid = me.getUnitsGrid(),
                plugin = grid.getPlugin('unitRowEditor');

            store.suspendAutoSync();

            // do the job, to be notified of end of editing
            grid.mon(grid, 'edit', function (editor, event) {
                event.record.set('warehouse_id', warehouses.getActive().getId());
                event.record.save({
                    callback: function () {
                        store.resumeAutoSync();
                        loadWarehouseStore(store, me.getOverview(), true, me);
                        grid.mun(grid, 'edit');

                        Ext.getCmp('statusBar').setStatus({
                            text : 'Właśnie dodałeś nową strefę...',
                            clear: {
                                wait       : 10000,
                                anim       : true,
                                useDefaults: false
                            }
                        });
                    }
                });
            });

            var record = store.add(Ext.create('WMS.model.entity.Unit'));
            if (!Ext.isDefined(record)) {
                console.error('Overview :: Failed to add new unit when row edition enabled');
            }

            plugin.startEdit(store.getTotalCount(), store.getTotalCount());

        },
        onUnitDelete           : function () {
            var me = this,
                store = me.getUnitsStore(),
                grid = me.getUnitsGrid(),
                selection = grid.getView().getSelectionModel().getSelection();

            if (selection) {
                store.remove(selection);
                loadWarehouseStore(store, me.getOverview(), true, me);
                Ext.getCmp('statusBar').setStatus({
                    text : Ext.String.format('Usunąłeś {0} {1}',
                        selection.length,
                        selection.length > 0 ? 'stref' : 'strefę'),
                    clear: {
                        wait       : 10000,
                        anim       : true,
                        useDefaults: false
                    }
                });
            }
        }
    }
});
