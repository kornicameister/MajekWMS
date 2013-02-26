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
        onStatisticsTabRender = function (statisticPanel) {
            var me = this,
                unitChart = statisticPanel.down('chart');

            me.setUnitUsageChart.apply(me, [unitChart]);
        };
    return {
        extend           : 'Ext.app.Controller',
        views            : [
            'WMS.view.wms.Statistics'
        ],
        stores           : [
            'Warehouses'
        ],
        init             : function () {
            console.init('WMS.controller.wms.Statistics initializing...');
            var me = this;
            me.control({
                '#viewport masterview wmsstatistics': {
                    'afterrender': onStatisticsTabRender
                }
            }, me)
        },
        setUnitUsageChart: function (unitPieChart) {
            var me = this,
                pieChartStore = unitPieChart.getStore(),
                unitStore = me.getWarehousesStore().getActive().units();

            unitPieChart.bindStore(translateUnitStore(pieChartStore, unitStore));
        }
    }
});