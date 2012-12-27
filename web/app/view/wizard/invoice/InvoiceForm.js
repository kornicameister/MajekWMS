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
            maxLength : 45,
            minWidth  : 150,
            allowBlank: false
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
            title : 'Dane podstawowe',
            itemId: 'basicInformation',
            items : [
                {
                    itemId    : 'invoiceNumber',
                    fieldLabel: 'Numer faktury',
                    name      : 'invoiceNumber',
                    readOnly  : true
                },
                {
                    xtype        : 'combo',
                    itemId       : 'clientsCB',
                    fieldLabel   : 'Klient',
                    store        : undefined,
                    name         : 'client_id',
                    valueField   : 'id',
                    displayField : 'name',
                    typeAhead    : true,
                    triggerAction: 'all',
                    selectOnTab  : true,
                    allowBlank   : false,
                    tpl          : Ext.create('Ext.XTemplate',
                        '<tpl for=".">',
                        '<div class="x-boundlist-item">{name}  </br><strong style="color: darkblue">[ {company} ]</strong></div>',
                        '</tpl>'
                    )
                }
            ]
        },
        {
            title   : 'Daty',
            defaults: {
                xtype     : 'datefield',
                altFormats: 'd-m-Y',
                format    : 'm-d-Y',
                minValue  : new Date()
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
                        xtype: 'rownumberer'
                    },
                    {
                        header   : 'Produkt',
                        dataIndex: 'product_id',
                        width    : 130,
                        editor   : {
                            xtype        : 'combo',
                            id           : 'productInvoiceCB',
                            store        : undefined,
                            valueField   : 'id',
                            displayField : 'name',
                            typeAhead    : true,
                            triggerAction: 'all',
                            selectOnTab  : true,
                            lazyRender   : true,
                            listClass    : 'x-combo-list-small',
                            tpl          : Ext.create('Ext.XTemplate',
                                '<tpl for=".">',
                                '<div class="x-boundlist-item">{name}  </br>' +
                                    '<strong style="color: #0000cc; font-weight:bold">' +
                                    '[ Cena: {price} zł]</br>[ Podatek: {tax} %]' +
                                    '</strong></div>',
                                '</tpl>'
                            )
                        }
                    },
                    {
                        header     : 'Sztuk',
                        dataIndex  : 'quantity',
                        summaryType: 'sum',
                        width      : 50,
                        field      : {
                            xtype   : 'navnumberfield',
                            name    : 'quantity',
                            value   : 1,
                            maxValue: Number.MAX_VALUE,
                            step    : 10
                        }
                    },
                    {
                        header         : 'Cena',
                        dataIndex      : 'price',
                        summaryType    : 'average',
                        renderer       : function (val) {
                            return val + ' zł'
                        },
                        summaryRenderer: function (value) {
                            return value + ' zł';
                        },
                        width          : 50,
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
                        width    : 60,
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
                        header   : 'Cena VAT',
                        dataIndex: 'summaryPrice',
                        width    : 70
                    },
                    {
                        header   : 'Opis',
                        dataIndex: 'comment',
                        flex     : 3,
                        field    : {
                            xtype    : 'textarea',
                            name     : 'comment',
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
                itemId    : 'invoiceDescription',
                name      : 'description',
                flex      : 2,
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