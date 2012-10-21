/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:59
 */

Ext.define('WMS.controller.wms.unit.Inventory', {
    extend: 'Ext.app.Controller',

    stores: [
        'Measures'
    ],
    views : [
        'wms.unit.Inventory'
    ],
    refs  : [
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
                selectionchange: me.onProductSelectionChanged,
                afterrender    : me.onProductListAfterRender
            }
        });
    },

    onProductAdd: function () {
        console.log('Inventory :: Adding new product');
        var me = this,
            unit = me.getStore('Units').getActive(),
            store = unit.products(),
            grid = me.getProductList(),
            record = store.add(Ext.create('WMS.model.entity.Product', {
                unit_id: unit.getId()
            }));

        if (!Ext.isDefined(record)) {
            console.error('Overview :: Failed to add new product when row edition enabled');
        }

        grid.getPlugin('inventoryRowEditor').startEdit(store.getCount(), 1);

        Ext.getCmp('statusBar').setStatus({
            text : 'You\'ve just added new product...',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },

    onProductDelete: function () {
        console.log('Inventory :: Deleting already defined product');
        var me = this,
            store = me.getStore('Units').getActive().products(),
            grid = me.getProductList(),
            selection = grid.getView().getSelectionModel().getSelection();

        if (selection) {
            store.remove(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('You\'ve just deleted {0} {1}',
                    selection.length,
                    selection.length > 0 ? 'products' : 'product'),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    },

    onProductListAfterRender: function () {
        console.log('Inventory :: Product list rendered successfully');
    },

    onProductSelectionChanged: function (selModel, selections) {
        var me = this,
            grid = me.getProductList();
        grid.down('#delete').setDisabled(selections.length === 0);
    },

    loadProductsFromUnit: function (unit) {
        var me = this,
            grid = me.getProductList(),
            products = unit.products();

        if (products.getCount() > 0) {
            grid.reconfigure(products);
            grid.up('wmsunit').items.getAt(1).expand();
        }

    }
});