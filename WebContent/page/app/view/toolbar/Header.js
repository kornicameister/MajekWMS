/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 00:28
 * File   : Toolbar
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.Header', {
    extend            : 'Ext.toolbar.Toolbar',
    alternateClassName: 'WMS.Header',
    alias             : 'widget.wmstbar',
    defaultType       : 'splitbutton',
    defaults          : {
        margin: {
            top   : 1,
            left  : 5,
            right : 0,
            bottom: 1
        }
    },
    items             : [
        {
            xtype  : 'button',
            itemId : 'receiptButton',
            text   : 'Receipt',
            iconCls: 'view-toolbar-receiptButton'
        },
        {
            xtype  : 'button',
            itemId : 'releaseButton',
            text   : 'Release',
            iconCls: 'view-toolbar-releaseButton'
        },
        '-',
        {
            itemId : 'warehouseButton',
            iconCls: 'view-toolbar-warehouseButton',
            text   : 'Warehouse',
            menu   : {
                xtype      : 'menu',
                defaultType: 'button',
                items      : [
                    {
                        itemId: 'warehouseOverview',
                        text  : 'Overview'
                    }                            ,
                    {
                        itemId: 'warehouseStatistics',
                        text  : 'Statistics'
                    }
                ]
            }
        },
        {
            itemId : 'unitsButton',
            iconCls: 'view-toolbar-unitsButton',
            text   : 'Units',
            menu   : {
                xtype      : 'menu',
                defaultType: 'button',
                items      : [
                    {
                        itemId: 'mockUnit_1',
                        text  : 'Mock unit 1'
                    },
                    {
                        itemId: 'mockUnit_2',
                        text  : 'Mock unit 2'
                    },
                    {
                        itemId: 'mockUnit_3',
                        text  : 'Mock unit 3'
                    },
                    {
                        itemId: 'mockUnit_4',
                        text  : 'Mock unit 4'
                    }
                ]
            }
        },
        '-',
        {
            xtype  : 'button',
            itemId : 'inventoryButton',
            text   : 'Inventory',
            iconCls: 'view-toolbar-inventoryButton'
        },
        '->',
        {
            xtype  : 'button',
            itemId : 'settingsButton',
            text   : 'Settings',
            iconCls: 'view-toolbar-settingsButton'
        } ,
        {
            xtype  : 'button',
            itemId : 'helpButton',
            text   : 'Help',
            iconCls: 'view-toolbar-helpButton'
        }
    ]
});
