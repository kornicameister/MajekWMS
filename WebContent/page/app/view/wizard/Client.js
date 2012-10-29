/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:40
 */

Ext.define('WMS.view.wizard.Client', {
    extend: 'WMS.view.abstract.BaseDialog',
    alias : 'widget.wizardclient',
    title : 'Dodawanie nowego klienta',
    width : 500,

    items: {
        xtype: 'form',
        url  : 'wms/client/save',

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
                iconCls : 'view-toolbar-clientsButton-add',
                formBind: true, //only enabled once the form is valid
                disabled: true
            },
            {
                itemId  : 'cancel',
                text    : 'Anuluj',
                iconCls : 'view-button-cancel',
                formBind: false,
                disabled: false
            }
        ]
    }
});