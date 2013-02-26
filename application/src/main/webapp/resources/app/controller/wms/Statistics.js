/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:47
 */

Ext.define('WMS.controller.wms.Statistics', function () {
    var onStatisticsTabRender = function (statisticPanel) {
        var me = this,
            unitChart = statisticPanel.down('#unitUsage chart'),
            productChart = statisticPanel.down('#productsTotallyCool chart'),
            unitStore = me.getUnitStore(),
            productStore = me.getProductsStore(),
            warehouseId = me.getWarehousesStore().getActive().getId(),
            unitStoreMask = new Ext.LoadMask(unitChart, {
                msg: 'Ładowanie statystyk dla stref w toku...'
            }),
            productStoreMask = new Ext.LoadMask(productChart, {
                msg: 'Ładowanie statystyk dla produktów w toku...'
            }),
            sharedSortes = [
                new Ext.util.Sorter({
                    property : 'name',
                    direction: 'desc'
                })
            ];

        var task = new Ext.util.DelayedTask(function () {
            unitStore.load({
                scope   : me,
                filters : [
                    new Ext.util.Filter({
                        property: 'warehouse_id',
                        value   : warehouseId
                    })
                ],
                sorters : sharedSortes,
                callback: function () {
                    unitChart.bindStore(unitStore);
                    unitStoreMask.hide();
                }
            });
            productStore.load({
                scope   : me,
                sorters : sharedSortes,
                callback: function () {
                    productChart.bindStore(productStore);
                    productStoreMask.hide();
                }
            })
        });

        unitStoreMask.show();
        productStoreMask.show();

        task.delay(1250);
    };
    return {
        extend  : 'Ext.app.Controller',
        views   : [
            'WMS.view.wms.Statistics'
        ],
        requires: [
            'WMS.model.entity.Product',
            'WMS.model.entity.Unit'
        ],
        stores  : [
            'Warehouses'
        ],
        config  : {
            productsStore: undefined,
            unitStore    : undefined
        },
        init    : function () {
            console.init('WMS.controller.wms.Statistics initializing...');
            var me = this;

            me.setProductsStore(Ext.create('Ext.data.Store', {
                model   : 'WMS.model.entity.Product',
                autoSync: false
            }));
            me.setUnitStore(Ext.create('Ext.data.Store', {
                model   : 'WMS.model.entity.Unit',
                autoSync: false
            }));

            me.control({
                '#viewport masterview wmsstatistics': {
                    'activate': onStatisticsTabRender
                }
            }, me);
        }
    }
});