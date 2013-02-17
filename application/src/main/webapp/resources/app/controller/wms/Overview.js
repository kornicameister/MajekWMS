/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:42
 */

Ext.define('WMS.controller.wms.Overview', {
    extend                 : 'Ext.app.Controller',
    stores                 : [
        'UnitTypes',
        'Companies',
        'Warehouses'
    ],
    views                  : [
        'wms.Overview'
    ],
    refs                   : [
        {ref: 'unitsGrid', selector: 'wmsoverviews egrid[itemId=unitsGrid]'},
        {ref: 'warehouseDescription', selector: 'wmsoverviews propertygrid[itemId=description]'}
    ],
    init                   : function () {
        console.init('WMS.controller.wms.Overview initializing...');
        var me = this,
            companies = me.getCompaniesStore(),
            warehouses = me.getWarehousesStore();

        me.control({
            'wmsoverviews #add'                            : {
                click: me.onNewUnit
            },
            'wmsoverviews #delete'                         : {
                click: me.onUnitDelete
            },
            'wmsoverviews #unitsGrid'                      : {
                selectionchange: me.onUnitSelectionChanged
            },
            'wmsoverviews propertygrid[itemId=description]': {
                edit: me.onWarehousePropertyEdit
            }
        });

        me.mon(warehouses, 'activechanged', me.onActiveWarehouseChange, me);
        me.mon(companies, 'load', me.onCompaniesLoad, me);
    },
    onCompaniesLoad        : function () {
        var me = this,
            warehouses = me.getWarehousesStore();
        warehouses.load();
    },
    onWarehousePropertyEdit: function (editor, event) {
        var me = this,
            value = event['value'],
            field = event['record'].get('name');

        console.log(value);
    },
    onActiveWarehouseChange: function (store, activeWarehouse) {
        var me = this,
            units = activeWarehouse.units();

        console.log('Overview:: Active warehouse changed, switching to ' + activeWarehouse.get('name'));
        me.mon(units, 'load', me.onUnitsStoreLoaded, me);
        me.mon(units, 'update', me.onUnitsStoreUpdated);
    },
    onUnitsStoreUpdated    : function (store, records) {
        var length = records.length;
        Ext.getCmp('statusBar').setStatus({
            text : Ext.String.format('Uaktualniłeś {0} {1}', length, (length === 1 ? 'strefę' : 'stref')),
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },
    onUnitsStoreLoaded     : function (store) {
        var me = this,
            wd = me.getWarehouseDescription(),
            activeWarehouse = me.getWarehousesStore().getActive(),
            source = activeWarehouse.toSource(
                [
                    { field: 'name', header: 'Nazwa'},
                    { field: 'description', header: 'Opis'},
                    { field: 'usage', header: 'Wypełnienie'},
                    { field: 'size', header: 'Rozmiar'},
                    { field: 'createdDate', header: 'Utworzony'}
                ]
            );

        console.log('Overview :: Units`s changed, refreshing the unit\'s grid');

        me.getUnitsGrid().reconfigure(store);
        wd.setSource(source);
    },
    onUnitSelectionChanged : function (selModel, selections) {
        var grid = this.getUnitsGrid();
        grid.down('#delete').setDisabled(selections.length === 0);
    },
    onNewUnit              : function () {
        var me = this,
            activeWarehouse = me.getWarehousesStore().getActive(),
            store = activeWarehouse.units(),
            grid = me.getUnitsGrid(),
            record = store.add(Ext.create('WMS.model.entity.Unit'));

        if (!Ext.isDefined(record)) {
            console.error('Overview :: Failed to add new unit when row edition enabled');
        }

        grid.getPlugin('unitRowEditor').startEdit(store.getTotalCount(), store.getTotalCount());
        Ext.getCmp('statusBar').setStatus({
            text : 'Właśnie dodałeś nową strefę...',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },
    onUnitDelete           : function () {
        var me = this,
            activeWarehouse = me.getWarehousesStore().getActive(),
            store = activeWarehouse.units(),
            grid = me.getUnitsGrid(),
            selection = grid.getView().getSelectionModel().getSelection();

        if (selection) {
            store.remove(selection);
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
});
