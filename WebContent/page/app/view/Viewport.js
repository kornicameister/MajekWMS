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

    uses: [
        'wms.toolbar.footer',
        'wms.toolbar.header'
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
            xtype   : 'wms.toolbar.header',
            defaults: {
                iconAlign: 'left',
                scale    : 'large'
            }
        },
        fbar    : {
            xtype   : 'wms.toolbar.footer',
            defaults: {
                iconAlign: 'left',
                scale    : 'large'
            }
        }
    }
});
