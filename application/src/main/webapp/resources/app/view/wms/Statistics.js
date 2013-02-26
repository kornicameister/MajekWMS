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
                            'sharedUsage',
                            'name'
                        ],
                        data  : [
                            {
                                sharedUsage: 1,
                                name       : ''
                            }
                        ]
                    }
                ),
                series    : [
                    {
                        type        : 'pie',
                        angleField  : 'sharedUsage',
                        showInLegend: true,
                        highlight   : {
                            segment: {
                                margin: 20
                            }
                        },
                        tips        : {
                            trackMouse: true,
                            width     : 200,
                            height    : 40,
                            renderer  : function (storeItem) {
                                this.setTitle(
                                    Ext.String.format('Strefa {0}[{1} %]',
                                        storeItem.get('name'),
                                        Ext.util.Format.number(
                                            parseFloat(storeItem.get('sharedUsage') * 100.0),
                                            '0.00'
                                        )
                                    )
                                );
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
                        fields: [
                            'totalCount',
                            'name'
                        ],
                        data  : [
                            {
                                totalCount: 1,
                                name      : 'test'
                            }
                        ]
                    }
                ),
                axes      : [
                    {
                        type    : 'Numeric',
                        position: 'bottom',
                        fields  : [
                            'totalCount'
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
                                        storeItem.get('totalCount')
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
                            'totalCount'
                        ]
                    }
                ]
            }
        }
    ]
});