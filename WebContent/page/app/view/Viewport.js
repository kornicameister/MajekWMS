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
        'WMS.view.toolbar.Header'
    ],

    items: {
        id      : 'viewport',
        xtype   : 'panel',
        layout  : 'border',
        title   : 'WMS Simulator',
        defaults: {
            split : true,
            border: false
        },
        tbar    : {
            xtype   : 'wmstbar',
            defaults: {
                iconAlign: 'left',
                scale    : 'large'
            }
        },
        bbar    : {
            xtype      : 'wmsfbar',
            defaults   : {
                iconAlign: 'left',
                scale    : 'large'
            }
        }
    }
});
