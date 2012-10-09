/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 05.10.12
 * Time   : 11:36
 */

Ext.define('WMS.view.dialog.WarehouseSelector', {
    extend: 'WMS.view.dialog.BaseDialog',
    alias : 'widget.warehouseselector',
    title : 'Choose Warehouse',

    items: {
        xtype   : 'panel',
        layout  : {
            type : 'hbox',
            align: 'stretch',
            pack : 'center'
        },
        defaults: {
            flex: 1
        },
        items   : [
            {
                xtype : 'grid',
                itemId: 'warehouseSelectorGrid',
                store : 'Warehouses',

                columnWidth: 120,
                viewConfig : {
                    forceFit: true
                },

                columns: [
                    {
                        header   : 'Name',
                        dataIndex: 'name'
                    },
                    {
                        header   : 'Description',
                        dataIndex: 'description'
                    },
                    {
                        header   : 'Current size',
                        dataIndex: 'size',
                        flex     : 3
                    }
                ]
            }
        ]
    },
    fbar : [
        {
            itemId: 'warehouseSelectorOpenButton',
            xtype : 'button',
            text  : 'Open'
        }
    ]
});