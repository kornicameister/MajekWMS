/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 10:44
 */

Ext.define('WMS.view.manager.recipient.Manager', {
    extend            : 'Ext.panel.Panel',
    alternateClassName: 'WMS.panel.ClientManager',
    uses              : [
        'WMS.view.manager.abstract.DetailsHolder',
        'WMS.view.manager.abstract.ClientDetails',
        'WMS.view.manager.abstract.ClientGrid'
    ],
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
                    itemId : 'releaseClient',
                    text   : 'Nowe wydanie',
                    iconCls: 'view-toolbar-releaseButton'
                },
                '-',
                {
                    itemId : 'newClient',
                    text   : 'Dodaj',
                    iconCls: 'icon-add'
                },
                {
                    itemId : 'editClient',
                    text   : 'Edytuj',
                    iconCls: 'icon-edit'
                },
                {
                    itemId : 'removeClient',
                    text   : 'Usuń',
                    iconCls: 'icon-delete'
                },
                '-',
                {
                    itemId : 'detailsClient',
                    text   : 'Szczegóły',
                    iconCls: 'icon-details'
                }
            ]
        }
    ]
});