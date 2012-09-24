/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 04:39
 * File   : WMSView
 * Package: view
 * Created: 14-09-2012
 */

/**
 * @class WMS.view.WMSView
 * @extends Ext.panel.Panel
 *
 * Class represents top-level container for panels located underneath which presents
 * user information such as warehouse diagram etc.
 */
Ext.define('WMS.view.WMSView', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.wmsview',
    requires: [
        'WMS.view.wms.WMSInventory',
        'WMS.view.wms.WMSOverview',
        'WMS.view.wms.WMSStatistics',
        'WMS.view.wms.WMSUnit'
    ],

    title   : 'WMS - Warehouse',
    defaults: {
        autoScroll: true,
        margins   : {
            bottom: 2
        },
        margins   : '0 0 0 0'
    },
    layout  : {
        type            : 'accordion',
        titleCollapse   : false,
        animate         : true,
        activeOnTop     : true,
        hideCollapseTool: false
    },

    items: [
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
            xtype : 'wmsinventory',
            itemId: 'wmsinventory',
            title : "Inventory"
        },
        {
            xtype : 'wmsstatistics',
            itemId: 'wmsstatistics',
            title : 'Statistics'
        }
    ]
});
