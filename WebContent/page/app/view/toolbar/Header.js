/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 00:28
 * File   : Toolbar
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.Header', {
    itemId     : 'headerToolbar',
    extend     : 'Ext.toolbar.Toolbar',
    alias      : 'widget.headbar',
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

    items: [
        {
            xtype  : 'button',
            itemId : 'receiptButton',
            text   : 'Przyjęcia',
            iconCls: 'view-toolbar-receiptButton'
        },
        {
            xtype  : 'button',
            itemId : 'releaseButton',
            text   : 'Wydania',
            iconCls: 'view-toolbar-releaseButton'
        },
        '-',
        {
            itemId : 'warehouseButton',
            iconCls: 'view-toolbar-warehouseButton',
            text   : 'Magazyn',
            menu   : {
                xtype      : 'menu',
                defaultType: 'button',
                items      : [
                    {
                        itemId: 'warehouseOverview',
                        text  : 'Podgląd'
                    },
                    {
                        itemId: 'warehouseStatistics',
                        text  : 'Statystyki'
                    }
                ]
            }
        },
        {
            itemId : 'unitsButton',
            iconCls: 'view-toolbar-unitsButton',
            text   : 'Strefy',
            menu   : {
                xtype      : 'storemenu',
                defaultType: 'button',
                itemId     : 'unitMenu'
            }
        },
        '-',
        '->',
        {
            xtype  : 'button',
            itemId : 'settingsButton',
            text   : 'Ustawienia',
            iconCls: 'view-toolbar-settingsButton'
        }
    ]
});
