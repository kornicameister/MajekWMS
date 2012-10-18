/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 14.10.12
 * Time  : 22:34
 */

Ext.define('WMS.view.wms.unit.Canvas', {
    extend : 'Ext.panel.Panel',
    alias  : 'widget.wmsunitcanvas',
    iconCls: 'view-wms-unit-placement',

    layout: {
        type: 'fit'
    },

    items: {
        xtype    : 'draw',
        itemId   : 'unitsDrawingCmp'
    }
});