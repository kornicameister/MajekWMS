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
    uses   : [
        'WMS.view.wms.unit.Canvas',
        'WMS.view.wms.unit.Inventory'
    ],
    alias  : 'widget.wmsunit',
    iconCls: 'view-wms-unit',
    layout : {
        type   : 'accordion',
        pack   : 'center',
        stretch: 'max',
        animate: true
    },
    items  : [
        {
            xtype : 'wmsunitcanvas',
            itemId: 'wmsUnitSchema',
            title : 'Rozmieszczenie'
        },
        {
            xtype : 'wmsunitinventory',
            itemId: 'wmsInventory',
            title : 'Produkty',
            flex  : 3
        }
    ]
});