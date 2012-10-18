/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:50
 */

Ext.define('WMS.view.wms.unit.Inventory', {
    extend : 'Ext.grid.Panel',
    alias  : 'widget.wmsunitinventory',
    iconCls: 'view-wms-inventory',

    title      : 'Inventory',
    itemId     : 'inventoryGrid',
    emptyText  : 'No products for selected unit...',
    columnWidth: 120,
    selModel   : {
        xtype: 'rowmodel',
        mode : 'MULTI'
    },
    columns    : [
        {header: 'ID'},
        {header: 'Name'}
    ]
});