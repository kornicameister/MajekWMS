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
    extend: 'Ext.Viewport',
    layout: 'fit',

    requires: [
        'WMS.view.toolbar.Footer',
        'WMS.view.toolbar.Header',
        'WMS.view.Navigation'
    ],

    items: {
        id      : 'viewport',
        xtype   : 'panel',
        layout  : 'border',
        title   : 'WMS Simulator',
        defaults: {
            collapsible: true
        },

        items: [
            {
                xtype : 'wmsnav',
                itemId: 'navigator',
                title : 'WMS - Navigator',

                width    : 200,
                margins  : {
                    right: 3
                },
                collapsed: false,

                region: 'west'
            },
            {
                xtype : 'panel',
                title : 'WMS - Warehouse',
                region: 'center'
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
