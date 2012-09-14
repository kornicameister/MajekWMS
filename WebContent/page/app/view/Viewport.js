/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 13.09.12
 * Time   : 23:38
 * File   : Viewport
 * Created: 13-09-2012
 */

// entry point for file
Ext.define('WMS.view.Viewport', {
    extend            : 'Ext.Viewport',
    alternateClassName: 'WMS.view.Viewport',
    layout            : 'fit',

    requires: [
        'WMS.view.toolbar.WMSFooter',
        'WMS.view.toolbar.WMSHeader',
        'WMS.view.WMSNavigation',
        'WMS.view.WMSView'
    ],

    items: {
        id    : 'viewport',
        xtype : 'panel',
        layout: 'border',
        title : 'WMS Simulator',
        items : [
            {
                xtype : 'wmsnav',
                itemId: 'navigator',
                title : 'WMS - Navigator',

                width      : 200,
                margins    : {
                    right: 3
                },
                collapsible: true,
                collapsed  : true,

                region: 'west'
            },
            {
                xtype   : 'wmsview',
                title   : 'WMS - Warehouse',
                region  : 'center',
                defaults: {
                    autoScroll: true,
                    margins   : {
                        bottom: 2
                    }
                },
                items   : [
                    {
                        xtype : 'wmsoverviews',
                        itemId: 'wmsOverview',
                        title : 'Overview'
                    },
                    {
                        xtype : 'wmsunit',
                        itemId: 'wmsunit',
                        title : 'Units'
                    },
                    {
                        xtype : 'wmsstatistics',
                        itemId: 'wmsstatistics',
                        title : 'Statistics'
                    },
                    {
                        xtype : 'wmsinventory',
                        itemId: 'wmsinventory',
                        title : "Inventory"
                    }
                ]
            }
        ],

        tbar: {
            xtype   : 'wmstbar',
            defaults: {
                iconAlign: 'left',
                scale    : 'large'
            }
        },
        bbar: {
            xtype   : 'wmsfbar',
            defaults: {
                iconAlign: 'left',
                scale    : 'large'
            }
        }
    }
});
