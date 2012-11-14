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
Ext.define('WMS.view.Master', {
    extend        : 'Ext.tab.Panel',
    uses          : [
        'WMS.view.wms.Overview',
        'WMS.view.wms.Unit',
        'WMS.view.wms.Statistics'
    ],
    alias         : 'widget.masterview',
    defaults      : {
        autoScroll: true
    },
    deferredRender: true,
    items         : [
        {
            xtype : 'wmsoverviews',
            itemId: 'wmsOverview',
            title : 'Podgląd'
        },
        {
            xtype : 'wmsunit',
            itemId: 'wmsUnit',
            title : 'Strefy'
        },
        {
            xtype : 'wmsstatistics',
            itemId: 'wmsStatistics',
            title : 'Statystyki'
        }
    ]
});
