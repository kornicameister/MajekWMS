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

    items: {
        id    : 'viewport',
        xtype : 'panel',
        layout: 'accordion',
        title : 'WMS Simulator',

        items: {
            xtype: 'masterview'
        },

        tbar: {
            xtype: 'headbar'
        },
        bbar: {
            xtype: 'footbar'
        }
    }
});
