/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 00:28
 * File   : Toolbar
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.Header', {
    extend     : 'Ext.toolbar.Toolbar',
    requires   : [
        'Ext.ux.menu.StoreMenu'
    ],
    itemId     : 'headerToolbar',
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
    items      : [
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
        {
            itemId : 'clientsButton',
            iconCls: 'view-toolbar-clientsButton',
            text   : 'Odbiorcy',
            menu   : {
                xtype      : 'menu',
                defaultType: 'button',
                itemId     : 'clientsMenu',
                items      : [
                    {
                        itemId : 'addRecipient',
                        text   : 'Nowy odbiorca',
                        iconCls: 'icon-add'
                    },
                    {
                        itemId : 'addReceiptInvoice',
                        text   : 'Nowe wydanie magazynowe',
                        iconCls: 'icon-add'
                    }
                ]
            }
        },
        {
            itemId : 'suppliers',
            iconCls: 'view-toolbar-suppliersButton',
            text   : 'Dostawcy',
            menu   : {
                xtype      : 'menu',
                defaultType: 'button',
                itemId     : 'suppliersMenu',
                items      : [
                    {
                        itemId : 'addSupplier',
                        text   : 'Nowy dostawca',
                        iconCls: 'icon-add'
                    },
                    {
                        itemId : 'addSupplyInvoice',
                        text   : 'Nowa przyjęcie magazynowe',
                        iconCls: 'icon-add'
                    }
                ]
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
