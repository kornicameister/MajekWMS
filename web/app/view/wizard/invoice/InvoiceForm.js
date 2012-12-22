/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.12.12
 * Time     : 10:27
 */

/**
 * @author kornicameister
 * @version 0.1
 * @description Component that defines form (GUI) used to
 * to create and save new invoice.
 */
Ext.define('WMS.view.wizard.invoice.InvoiceForm', {
    extend      : 'Ext.form.Panel',
    uses        : [
        'WMS.view.abstract.EditableGrid',
        'WMS.view.abstract.NavigableNumberField',
        'WMS.store.InvoiceTypes',
        'WMS.store.Clients'
    ],
    alias       : 'widget.invoiceform',
    monitorValid: true,
    autoScroll  : true,
    defaultType : 'fieldset',
    //defaults applied to all underlying fieldset
    defaults    : {
        defaultType  : 'textfield',
        padding      : 5,
        fieldDefaults: {
            labelWidth: 55,
            labelAlign: 'right',
            msgTarget : 'side'
        },
        layout       : {
            type : 'hbox',
            pack : 'center',
            align: 'stretch'
        },
        defaults     : {
            maxLength  : 45,
            minWidth   : 150,
            allowBlank : false
        }
    },
    //defaults applied to all underlying fieldset
    layout      : {
        type : 'vbox',
        pack : 'center',
        align: 'stretch'
    },
    items       : [
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
                    fieldLabel: 'Wystawiona dnia',
                    name      : 'createdDate'
                },
                {
                    fieldLabel: 'Termin płatności',
                    name      : 'dueDate'
                }
            ]
        },
        {
            title : 'Produkty',
            height: 275,
            items : {
                xtype    : 'egrid',
                itemId   : 'invoiceProductsGrid',
                emptyText: 'Obecnie ta faktura jest pusta..',
                flex     : 2,
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
                        renderer : Ext.ux.WMSColumnRenderers.measureColumnRenderer,
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
            title: 'Opis',
            items: {
                xtype     : 'textarea',
                flex      : 2,
                itemId    : 'invoiceDescription',
                fieldLabel: 'Opcjonalny opis',
                emptyText : 'Wprowadź tutaj opcjonalny opis...'
            }
        }
    ],
    buttons     : [
        {
            itemId  : 'submit',
            text    : 'Wykonaj',
            iconCls : 'icon-add',
            formBind: true,
            disabled: true
        },
        {
            itemId  : 'reset',
            text    : 'Wyczyść',
            iconCls : 'view-wizard-reset',
            formBind: true,
            disabled: false
        },
        {
            itemId  : 'cancel',
            text    : 'Anuluj',
            iconCls : 'icon-cancel',
            formBind: false,
            disabled: false
        }
    ]
});