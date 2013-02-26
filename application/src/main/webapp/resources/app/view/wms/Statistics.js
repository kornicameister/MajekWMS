/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 04:37
 * File   : WarehouseStatistics
 * Package: view.wms
 * Created: 14-09-2012
 */

Ext.define('WMS.view.wms.Statistics', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.wmsstatistics',
    iconCls : 'view-wms-statistics',
    layout  : {
        type : 'hbox',
        pack : 'start',
        align: 'stretch'
    },
    defaults: {
        flex   : 1,
        xtype  : 'panel',
        margins: '35 5 5 0',
        layout : 'fit'
    },
    items   : [
        {
            itemId: 'unitUsage',
            items : {
                xtype     : 'chart',
                scrollable: true,
                defaults  : {
                    anchor : '100%',
                    style  : 'background:#fff',
                    animate: true,
                    shadow : true
                },
                store     : Ext.create('Ext.data.Store', {
                    fields: [
                        'angle',
                        'name'
                    ],
                    data  : [
                        {
                            angle: 1,
                            name : ''
                        }
                    ],
                    proxy : {
                        type: 'memory',
                        id  : 'translate-unit-pie-chart-store'
                    }}),
                series    : [
                    {
                        type        : 'pie',
                        angleField  : 'angle',
                        showInLegend: true,
                        highlight   : {
                            segment: {
                                margin: 20
                            }
                        },
                        label       : {
                            field   : 'name',
                            display : 'rotate',
                            contrast: true,
                            font    : '18px Arial'
                        }
                    }
                ]
            }
        }
    ]
});