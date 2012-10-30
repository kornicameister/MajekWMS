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
        'WMS.view.wizard.client.Form'
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
            store     : 'Clients',
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
            xtype  : 'form',
            itemId : 'clientDetailed',
            title  : 'Szczegóły',
            iconCls: 'icon-details'
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
                xtype : 'button',
                width : 120,
                height: 40
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