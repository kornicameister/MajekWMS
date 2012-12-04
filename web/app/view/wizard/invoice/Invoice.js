/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.11.12
 * Time     : 03:01
 */

Ext.define('WMS.view.wizard.invoice.Invoice', {
    extend            : 'WMS.view.abstract.BaseDialog',
    requires          : [
        'WMS.view.abstract.BaseDialog'
    ],
    uses              : [
        'WMS.view.abstract.EditableGrid',
        'WMS.view.abstract.NavigableNumberField',
        'WMS.store.InvoiceTypes',
        'WMS.store.Clients',
        'WMS.utilities.ColumnRenderers'
    ],
    alternateClassName: [
        'WMS.wizard.Invoice'
    ],
    autoShow          : true,
    autoRender        : true,
    width             : 800,
    minHeight         : 200,
    autoScroll        : true,
    items             : {
        xtype        : 'form',
        itemId       : 'invoiceForm',
        bodyPadding  : 5,
        waitMsgTarget: true,
        defaultType  : 'fieldset',
        defaults     : {
            minHeight: 40,
            maxHeight: 50,
            layout   : {
                xtype: 'hbox',
                pack : 'center',
                align: 'stretch'
            }
        },
        items        : [
            {
                title: 'Dane podstawowe',
                items: [
                    {
                        xtype     : 'textfield',
                        fieldLabel: 'Numer faktury',
                        name      : 'refNumber',
                        allowBlank: false,
                        value     : 'data + id'
                    },
                    {
                        xtype        : 'combo',
                        itemId       : 'invoiceTypeId',
                        fieldLabel   : 'Typ',
                        name         : 'type_id',
                        store        : undefined,
                        valueField   : 'id',
                        displayField : 'name',
                        typeAhead    : true,
                        triggerAction: 'all',
                        selectOnTab  : true
                    },
                    {
                        xtype        : 'combo',
                        itemId       : 'clientId',
                        fieldLabel   : 'Klient',
                        name         : 'client_id',
                        store        : undefined,
                        valueField   : 'id',
                        displayField : 'company',
                        typeAhead    : true,
                        triggerAction: 'all',
                        selectOnTab  : true
                    }
                ]
            },
            {
                title   : 'Daty',
                defaults: {
                    xtype: 'datefield'
                },
                items   : [
                    {
                        items: [
                            {
                                fieldLabel: 'Wystawiona dnia',
                                name      : 'createdDate'
                            },
                            {
                                fieldLabel: 'Termin płatności',
                                name      : 'dueDate'
                            }
                        ]
                    }
                ]
            },
            {
                title: 'Produkty',
                items: {
                    xtype    : 'egrid',
                    itemId   : 'invoiceProductsGrid',
                    emptyText: 'Obecnie ta faktura jest pusta..',
                    flex     : 3,
                    columns  : [
                        {
                            header   : 'ID',
                            dataIndex: 'id',
                            width    : 20,
                            editable : false,
                            hidden   : true
                        },
                        {
                            header     : 'Nazwa',
                            dataIndex  : 'name',
                            summaryType: 'count',
                            field      : {
                                xtype: 'textfield',
                                name : 'name'
                            }
                        },
                        {
                            header     : 'PJŁ',
                            dataIndex  : 'pallets',
                            summaryType: 'sum',
                            sortable   : true,
                            field      : {
                                xtype   : 'navnumberfield',
                                name    : 'pallets',
                                value   : 1,
                                maxValue: Number.MAX_VALUE,
                                step    : 1
                            }
                        },
                        {
                            header     : 'Ilość',
                            dataIndex  : 'quantity',
                            summaryType: 'average',
                            field      : {
                                xtype   : 'navnumberfield',
                                name    : 'maximumSize',
                                value   : 100,
                                maxValue: Number.MAX_VALUE,
                                step    : 200
                            }
                        },
                        {
                            header   : 'Jednostka',
                            dataIndex: 'measure_id',
                            width    : 120,
                            renderer : ColumnRenderers.measureColumnRenderer,
                            editor   : {
                                xtype        : 'combo',
                                store        : 'Measures',
                                valueField   : 'id',
                                displayField : 'name',
                                typeAhead    : true,
                                triggerAction: 'all',
                                selectOnTab  : true,
                                lazyRender   : true,
                                listClass    : 'x-combo-list-small'
                            }
                        },
                        {
                            header         : 'Cena',
                            dataIndex      : 'price',
                            summaryType    : 'average',
                            summaryRenderer: function (value) {
                                return value + ' zł';
                            },
                            field          : {
                                xtype   : 'navnumberfield',
                                name    : 'price',
                                value   : 22,
                                maxValue: Number.MAX_VALUE,
                                step    : 0.1
                            }
                        },
                        {
                            header   : 'Podatek',
                            dataIndex: 'tax',
                            renderer : function (value) {
                                return value + ' %';
                            },
                            field    : {
                                xtype   : 'navnumberfield',
                                name    : 'tax',
                                value   : 22,
                                minValue: 10,
                                maxValue: 100,
                                step    : 0.5
                            }
                        },
                        {
                            header   : 'Opis',
                            dataIndex: 'description',
                            flex     : 3,
                            field    : {
                                xtype    : 'textarea',
                                name     : 'description',
                                maxLength: 250
                            }
                        }
                    ],
                    plugins  : [
                        Ext.create('Ext.grid.plugin.RowEditing', {
                            pluginId: 'invoiceProductRowEditor'
                        })
                    ]
                }
            },
            {
                title      : 'Opis',
                collapsed  : true,
                collapsible: true,
                items      : {
                    xtype     : 'textarea',
                    fieldLabel: 'Opcjonalny opis',
                    emptyText : 'Wprowadź tutaj opcjonalny opis...'
                }
            }
        ]
    }
});
