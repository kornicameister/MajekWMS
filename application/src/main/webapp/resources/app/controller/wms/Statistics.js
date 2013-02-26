/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:47
 */

Ext.define('WMS.controller.wms.Statistics', function () {
    var translateUnitStore = function (pieChartStore, unitStore) {
            pieChartStore.removeAll();
            pieChartStore.loadData((
                function () {
                    var data = [];
                    unitStore.each(function (record) {
                        data.push({
                            angle: record.get('sharedUsage'),
                            name : Ext.String.format('Strefa {0}/{1}', record.get('name'), record.getType().get('name'))
                        });
                    });
                    return data;
                }()
                ));
            return pieChartStore;
        },
        translateProductStore = function (barChartStore, productStore) {
            barChartStore.removeAll();
            barChartStore.loadData((
                function () {
                    var data = [];
                    productStore.each(function (record) {
                        data.push({
                            amount: record.get('totalCount'),
                            name  : record.get('name')
                        });
                    });
                    return data;
                }()
                ));
            return barChartStore;
        },
        onStatisticsTabRender = function (statisticPanel) {
            var me = this,
                unitChart = statisticPanel.down('#unitUsage chart'),
                productChart = statisticPanel.down('#productsTotallyCool chart');

            me.setUnitUsageChart.apply(me, [unitChart]);
            me.setProductAmountChart.apply(me, [productChart]);
        };
    return {
        extend               : 'Ext.app.Controller',
        views                : [
            'WMS.view.wms.Statistics'
        ],
        requires             : [
            'WMS.model.entity.Product'
        ],
        stores               : [
            'Warehouses'
        ],
        config               : {
            productsStore: undefined
        },
        init                 : function () {
            console.init('WMS.controller.wms.Statistics initializing...');
            var me = this;

            me.setProductsStore(Ext.create('Ext.data.Store', {
                model   : 'WMS.model.entity.Product',
                autoSync: false,
                autoLoad: true
            }));

            me.control({
                '#viewport masterview wmsstatistics': {
                    'afterrender': onStatisticsTabRender
                }
            }, me);
        },
        setUnitUsageChart    : function (unitPieChart) {
            var me = this,
                pieChartStore = unitPieChart.getStore(),
                unitStore = me.getWarehousesStore().getActive().units();

            unitPieChart.bindStore(translateUnitStore(pieChartStore, unitStore));
        },
        setProductAmountChart: function (productBarChart) {
            var me = this,
                store = productBarChart.getStore(),
                productsStore = me.getProductsStore();

            productBarChart.bindStore(translateProductStore(store, productsStore));
        }
    }
});