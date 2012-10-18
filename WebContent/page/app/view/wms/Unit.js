/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 04:38
 * File   : WMSUnit
 * Package: view.wms
 * Created: 14-09-2012
 */

Ext.define('WMS.view.wms.Unit', {
    extend : 'Ext.panel.Panel',
    alias  : 'widget.wmsunit',
    iconCls: 'view-wms-unit',
    layout : {
        type: 'accordion'
    },
    items  : [
        {
            xtype : 'wmsunitcanvas',
            itemId: 'wmsUnitSchema',
            title : 'Unit\'s placement'
        },
        {
            xtype : 'wmsinventory',
            itemId: 'wmsInventory',
            title : "Inventory"
        }
    ]
});