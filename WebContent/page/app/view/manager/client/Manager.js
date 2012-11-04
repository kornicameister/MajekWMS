/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 14:45
 */

Ext.define('WMS.view.manager.client.Manager', {
    extend : 'Ext.panel.Panel',
    alias  : 'widget.clientmanager',
    title  : 'Menadżer - odbiorcy',
    iconCls: 'view-toolbar-clientsButton',

    uses: [
        'WMS.view.wizard.client.Form',
        'WMS.view.manager.abstract.DetailsHolder',
        'WMS.view.manager.client.details.Client',
        'WMS.view.manager.client.details.Receipts',
        'WMS.view.manager.client.details.Releases'
    ],

    closable   : true,
    layout     : {
        type: 'accordion'
    },
    items      : [
        {
            xtype     : 'grid',
            itemId    : 'clientList',
            title     : 'Odbiorcy',
            iconCls   : 'view-toolbar-clientsButton',
            autoScroll: true,
            viewConfig: {
                emptyText: 'Nie zdefiniowano jeszcze żadnego odbiorcy...'
            },
            store     : 'Recipients',
            columns   : [
                {
                    header   : 'Skrót',
                    dataIndex: 'name',
                    width    : 160
                },
                {
                    header   : 'Firma',
                    dataIndex: 'company',
                    width    : 260
                },
                {
                    header   : 'Szczegóły',
                    dataIndex: 'description',
                    flex     : 3
                }
            ]
        },
        {
            xtype : 'managerdetailsholder',
            itemId: 'clientDetailed',
            items : [
                {
                    xtype : 'clientdetails',
                    itemId: 'clientDetails',
                    flex  : 1
                },
                {
                    xtype : 'clientreleases',
                    itemId: 'clientReleases',
                    flex  : 2
                },
                {
                    xtype : 'clientreceipts',
                    itemId: 'clientsReceipts',
                    flex  : 2
                }
            ]
        }
    ],
    dockedItems: [
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
                    text   : 'Nowy odbiorca',
                    iconCls: 'icon-add'
                },
                {
                    itemId : 'editClient',
                    text   : 'Edytuj odbiorcę',
                    iconCls: 'icon-edit'
                },
                {
                    itemId : 'removeClient',
                    text   : 'Usuń odbiorcę',
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