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

    requires: [
        'WMS.view.wizard.client.Form'
    ],

    closable  : true,
    layout    : {
        type          : 'border',
        menageOverflow: 2,
        split         : true
    },
    bodyBorder: false,
    defaults  : {
        margins    : '5 0 0 0',
        collapsible: true,
        bodyPadding: 10
    },

    items: [
        {
            xtype    : 'clientform',
            title    : 'Nowy odbiorca',
            region   : 'west',
            collapsed: false,
            width    : 400,
            maxWidth : 450,
            minWidth : 350,
            floatable: false,
            iconCls  : 'icon-add'
        },
        {
            xtype      : 'panel',
            itemId     : 'lists',
            region     : 'center',
            collapsible: false,
            layout     : {
                type: 'accordion'
            },
            items      : [
                {
                    xtype      : 'grid',
                    itemId     : 'clientList',
                    title      : 'Odbiorcy',
                    iconCls    : 'view-toolbar-clientsButton',
                    multiSelect: true,
                    autoScroll : true,
                    viewConfig : {
                        emptyText: 'Nie zdefiniowano jeszcze żadnego odbiorcy...'
                    },
                    store      : 'Clients',
                    columns    : [
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
            ]
        }
    ]
});