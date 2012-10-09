/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 05.10.12
 * Time   : 11:36
 */

Ext.define('WMS.view.dialog.WarehouseSelector', {
    extend: 'Ext.window.Window',
    alias : 'widget.warehouseselector',
    title : 'Choose Warehouse',
    width : 440,

    defaults: {
        border: false,
        frame : false
    },
    layout  : 'fit',

    items: {
        xtype : 'panel',
        layout: {
            type : 'hbox',
            align: 'stretch'
        },
        items : [
            {
                xtype: 'list'
            },
            {
                xtype: 'form'
            }
        ]
    }
})