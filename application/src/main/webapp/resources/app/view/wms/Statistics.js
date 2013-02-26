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
        flex    : 1,
        xtype   : 'panel',
        margins : '35 5 5 0',
        layout  : 'fit',
        defaults: {
            animate: true,
            shadow : true
        }
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
                        }
                    }
                ),
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
        },
        {
            itemId: 'productsTotallyCool',
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
                        fields  : [
                            'amount',
                            'name'
                        ],
                        data    : [
                            {
                                amount: 1,
                                name  : 'test'
                            }
                        ],
                        autoSync: true,
                        proxy   : {
                            type: 'memory',
                            id  : 'translate-product-bar-chart'
                        }
                    }
                ),
                axes      : [
                    {
                        type    : 'Numeric',
                        position: 'bottom',
                        fields  : [
                            'amount'
                        ],
                        label   : {
                            renderer: Ext.util.Format.numberRenderer('0,0')
                        },
                        title   : 'Liczba produktów',
                        grid    : true,
                        minimum : 0
                    },
                    {
                        type    : 'Category',
                        position: 'left',
                        fields  : [
                            'name'
                        ],
                        title   : 'Produkt'
                    }
                ],
                background: {
                    gradient: {
                        id   : 'backgroundGradient',
                        angle: 45,
                        stops: {
                            0  : {
                                color: '#ffffff'
                            },
                            100: {
                                color: '#eaf1f8'
                            }
                        }
                    }
                },
                series    : [
                    {
                        type     : 'bar',
                        axis     : 'bottom',
                        highlight: true,
                        tips     : {
                            trackMouse: true,
                            width     : 200,
                            height    : 40,
                            renderer  : function (storeItem) {
                                this.setTitle(
                                    Ext.String.format('Product {0}, palet={1}',
                                        storeItem.get('name'),
                                        storeItem.get('amount')
                                    )
                                );
                            }
                        },
                        label    : {
                            display      : 'insideEnd',
                            field        : 'data1',
                            renderer     : Ext.util.Format.numberRenderer('0'),
                            orientation  : 'horizontal',
                            color        : '#333',
                            'text-anchor': 'middle'
                        },
                        xField   : 'name',
                        yField   : [
                            'amount'
                        ]
                    }
                ]
            }
        }
    ]
});