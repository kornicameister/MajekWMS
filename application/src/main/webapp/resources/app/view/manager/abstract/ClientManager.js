/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 10:44
 */

Ext.define('WMS.view.manager.abstract.ClientManager', {
    extend            : 'Ext.panel.Panel',
    uses              : [
        'WMS.view.manager.abstract.DetailsHolder',
        'WMS.view.manager.abstract.ClientDetails',
        'WMS.view.manager.abstract.ClientGrid',
        'WMS.view.manager.abstract.InvoiceList'
    ],
    alternateClassName: 'WMS.panel.ClientManager',
    closable          : true,
    layout            : {
        type: 'accordion'
    },
    dockedItems       : [
        {
            xtype   : 'toolbar',
            dock    : 'bottom',
            layout  : {
                type: 'hbox',
                pack: 'center'
            },
            defaults: {
                xtype    : 'button',
                width    : 120,
                height   : 40,
                iconAlign: 'left',
                scale    : 'large'
            },
            items   : [
                {
                    itemId : 'newClient',
                    text   : 'Dodaj',
                    iconCls: 'icon-add'
                },
                {
                    itemId : 'newInvoice',
                    text   : 'Nowa faktura',
                    iconCls: 'invoice-add'
                },
                {
                    itemId : 'removeClient',
                    text   : 'Usuń odbiorcę',
                    iconCls: 'icon-delete'
                },
                {
                    itemId : 'detailsClient',
                    text   : 'Szczegóły dla odbiorcy',
                    iconCls: 'icon-details',
                    width  : 200
                }
            ]
        }
    ]
});
