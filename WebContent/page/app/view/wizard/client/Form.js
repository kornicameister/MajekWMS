/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:40
 */

Ext.define('WMS.view.wizard.client.Form', {
    extend      : 'Ext.form.Panel',
    alias       : 'widget.clientform',
    url         : 'wms/client/save',
    uses        : [
        'WMS.model.entity.City',
        'WMS.model.entity.ClientType'
    ],
    monitorValid: true,
    autoScroll  : true,
    plain       : true,
    defaultType : 'fieldset',
    defaults    : {
        defaultType  : 'textfield',
        fieldDefaults: {
            labelWidth: 55,
            msgTarget : 'side'
        },
        defaults     : {
            maxLength : 45,
            width     : 350,
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
            title   : 'Typ',
            disabled: true,
            items   : {
                xtype        : 'combo',
                itemId       : 'typeId',
                fieldLabel   : 'Typ',
                name         : 'type_id',
                store        : 'ClientTypes',
                valueField   : 'id',
                displayField : 'name',
                typeAhead    : true,
                triggerAction: 'all',
                selectOnTab  : true
            }
        },
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
                    xtype        : 'combo',
                    fieldLabel   : 'Miejscowość',
                    name         : 'city_id',
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