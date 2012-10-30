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

    defaultType: 'textfield',
    defaults   : {
        allowBlank: false
    },
    layout     : {
        type : 'vbox',
        pack : 'center',
        align: 'stretch'
    },
    items      : [
        {
            fieldLabel: 'Nazwa',
            name      : 'name',
            emptyText : 'Nazwa użytkownika [skrót]',
            maxLength : 45
        },
        {
            fieldLabel: 'Firma',
            name      : 'company',
            emptyText : 'Firma [właściwa nazwa]',
            maxLength : 45
        },
        {
            xtype     : 'textarea',
            fieldLabel: 'Dodatkowe informacje',
            name      : 'description',
            emptyText : 'Jakiekolwiek dodatkowe informacje na ' +
                'temat tego klienta',
            maxLength : 200
        }
    ],
    buttons    : [
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