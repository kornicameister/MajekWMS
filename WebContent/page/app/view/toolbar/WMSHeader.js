/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 00:28
 * File   : Toolbar
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.WMSHeader', {
    extend     : 'Ext.toolbar.Toolbar',
    alias      : 'widget.wmstbar',
    defaultType: 'splitbutton',
    defaults   : {
        margin    : {
            top   : 1,
            left  : 5,
            right : 0,
            bottom: 1
        },
        iconAlign : 'left',
        scale     : 'large',
        arrowAlign: 'bottom'
    },
    items      : [
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
                        text  : 'Overview',
                        hash  : '4b30170a7a926c88b4fa7e10e44bf5fe'
                    }                            ,
                    {
                        itemId: 'warehouseStatistics',
                        text  : 'Statistics',
                        hash  : '30848088f40ae9bc47c289732597f4e2'
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
