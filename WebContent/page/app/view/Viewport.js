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
        'WMS.view.Toolbar'
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
            xtype   : 'wmstoolbar',
            defaults: {
                iconAlign : 'left',
                scale     : 'large',
                arrowAlign: 'bottom'
            }
        }
    }
});
