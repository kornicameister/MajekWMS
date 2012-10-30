/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:40
 */

Ext.define('WMS.view.wizard.client.Form', {
    extend: 'Ext.form.Panel',
    alias : 'widget.clientform',
    url   : 'wms/client/save',

    monitorValid: true,
    autoScroll  : true,
    defaultType : 'fieldset',
    defaults    : {
        defaultType: 'textfield',
        collapsible: true,
        collapsed  : true,
        defaults   : {
            maxLength : 45,
            width     : 300,
            allowBlank: false
        }
    },
    layout      : {
        type : 'vbox',
        pack : 'center',
        align: 'stretch'
    },
    items       : [
        {
            title    : 'Podstawowe informacje',
            collapsed: false,
            items    : [
                {
                    fieldLabel: 'Nazwa',
                    name      : 'name',
                    emptyText : 'Nazwa użytkownika [skrót]'
                },
                {
                    fieldLabel: 'Firma',
                    name      : 'company',
                    emptyText : 'Firma [właściwa nazwa]'
                },
                {
                    xtype     : 'textarea',
                    fieldLabel: 'Dodatkowe informacje',
                    name      : 'description',
                    emptyText : 'Jakiekolwiek dodatkowe informacje na ' +
                        'temat tego klienta',
                    maxLength : 200
                }
            ]
        },
        {
            title: 'Dane adresowe',
            items: [
                {
                    fieldLabel: 'Ulica',
                    name      : 'street'
                },
                {
                    fieldLabel: 'Kod pocztowy',
                    name      : 'postcode'
                },
                {
                    xtype     : 'combo',
                    fieldLabel: 'Miejscowość',
                    name      : 'city',

                    store        : 'Cities',
                    valueField   : 'id',
                    displayField : 'name',
                    typeAhead    : true,
                    triggerAction: 'all',
                    selectOnTab  : true
                }
            ]
        },
        {
            title: 'Dane firmy',
            items: [
                {
                    fieldLabel: 'NIP',
                    name      : 'nip'
                },
                {
                    fieldLabel: 'Telefon',
                    name      : 'phone'
                },
                {
                    fieldLabel: 'Fax',
                    name      : 'fax'
                },
                {
                    fieldLabel: 'Numer konta',
                    name      : 'account'
                }
            ]
        }
    ],
    buttons     : [
        {
            itemId  : 'submit',
            text    : 'Dodaj',
            iconCls : 'icon-add',
            formBind: true, //only enabled once the form is valid
            disabled: true
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