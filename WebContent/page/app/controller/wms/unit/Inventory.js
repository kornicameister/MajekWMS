/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:59
 */

Ext.define('WMS.controller.wms.unit.Inventory', {
    extend: 'Ext.app.Controller',

    requires: [
        'WMS.view.abstract.NavigableNumberField'
    ],
    stores  : [
        'Measures'
    ],
    views   : [
        'wms.unit.Inventory'
    ],
    refs    : [
        { ref: 'productList', selector: 'wmsunitinventory' }
    ],

    init: function () {
        console.init('WMS.controller.wms.unit.Inventory initializing... ');
        var me = this;

        me.control({
            'wmsunitinventory #add'   : {
                click: me.onProductAdd
            },
            'wmsunitinventory #delete': {
                click: me.onProductDelete
            },
            'wmsunitinventory'        : {
                selectionchange: me.onProductSelectionChanged
            }
        });
    },

    onProductAdd: function () {
        var me = this,
            activeUnit = me.getStore('Units').getActive(),
            record = activeUnit.addProduct(Ext.create('WMS.model.entity.Product'));

        if (!Ext.isDefined(record)) {
            console.error('Overview :: Failed to add new product when row edition enabled');
        }

        me.getProductList()
            .getPlugin('inventoryRowEditor')
            .startEdit(activeUnit.getProductsCount() - 1, 1);

        Ext.getCmp('statusBar').setStatus({
            text : 'Właśnie stworzyłeś nowy produkt...',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },

    onProductDelete: function () {
        var me = this,
            activeUnit = me
                .getStore('Units')
                .getActive(),
            selection = me.getProductList()
                .getView()
                .getSelectionModel()
                .getSelection();

        if (selection) {
            activeUnit.deleteProduct(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Właśnie usunąłeś {0} {1}',
                    selection.length,
                    selection.length > 1 ? 'produktów' : 'produkt'),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    },

    onProductSelectionChanged: function (selModel, selections) {
        var me = this,
            grid = me.getProductList();
        grid.down('#delete').setDisabled(selections.length === 0);
    },

    loadProductsFromUnit: function (unit) {
        var me = this,
            grid = me.getProductList(),
            products = unit.products(),
            unitCtrl = me.getController('WMS.controller.wms.Unit');

        grid.reconfigure(products);
        unitCtrl.activateInventory();

        Ext.getCmp('statusBar').setStatus({
            text : Ext.String.format('Znaleziono {1} produktów w strefie {0} ...', unit.get('name'), products.getCount()),
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    }
});