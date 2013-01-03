/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:59
 */

Ext.define('WMS.controller.wms.unit.Inventory', {
    extend              : 'Ext.app.Controller',
    stores              : [
        'Measures'
    ],
    views               : [
        'wms.unit.Inventory'
    ],
    refs                : [
        { ref: 'productList', selector: 'wmsunitinventory' }
    ],
    init                : function () {
        console.init('WMS.controller.wms.unit.Inventory initializing... ');
    },
    loadProductsFromUnit: function (unit) {
        var me = this,
            unitsStore = Ext.StoreManager.lookup('Units'),
            productsStore = unitsStore.getProducts(unit),
            productsGrid = me.getProductList(),
            unitCtrl = me.getController('WMS.controller.wms.Unit');

        if (productsStore.getCount() > 0) {
            productsGrid.reconfigure(productsStore);
            unitCtrl.activateInventory();
        }

        Ext.getCmp('statusBar').setStatus({
            text : Ext.String.format('Znaleziono {1} produktów w strefie {0} ...', unit.get('name'), productsStore.getCount()),
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    }
});