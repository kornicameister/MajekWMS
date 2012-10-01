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
    layout            : 'fit',

    requires: [
        'WMS.view.toolbar.Footer',
        'WMS.view.toolbar.Header',
        'WMS.view.Navigation',
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
                region: 'west'
            },
            {
                xtype : 'wmsview',
                region: 'center'
            }
        ],

        tbar: {
            xtype: 'wmstbar'
        },
        bbar: {
            xtype: 'wmsfbar'
        }
    }
});
