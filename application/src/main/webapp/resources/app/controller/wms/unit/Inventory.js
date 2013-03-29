/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:59
 */

Ext.define('WMS.controller.wms.unit.Inventory', function () {
        var productListMask = undefined,
            maskProductList = function (view, store) {
                productListMask = new Ext.LoadMask(
                    view, {
                        msg  : 'Ładowanie produktów, proszę czekać...',
                        store: store
                    }
                )
            };
        return {
            extend              : 'Ext.app.Controller',
            stores              : [
                'Measures',
                'Products'
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
                    productsStore = me.getProductsStore(),
                    productsGrid = me.getProductList(),
                    unitCtrl = me.getController('WMS.controller.wms.Unit');

                var task = new Ext.util.DelayedTask(function () {
                    productsStore.load({
                        scope   : me,
                        filters : [
                            {
                                property: 'unit_id',
                                value   : unit.getId()
                            }
                        ],
                        callback: function (records) {
                            if (records.length > 0) {
                                productsGrid.reconfigure(productsStore);
                            }

                            Ext.getCmp('statusBar').setStatus({
                                text : Ext.String.format('Znaleziono {1} produktów w strefie {0} ...', unit.get('name'), records.length),
                                clear: {
                                    wait       : 10000,
                                    anim       : true,
                                    useDefaults: false
                                }
                            });
                        }
                    });
                });

                maskProductList(productsGrid, productsStore);
                unitCtrl.activateInventory();
                task.delay(1250);
            }
        }
    }
);