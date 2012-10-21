/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 05.10.12
 * Time   : 11:36
 */

Ext.define('WMS.view.dialog.WarehouseSelector', {
    extend   : 'WMS.view.abstract.BaseDialog',
    alias    : 'widget.warehouseselector',
    title    : 'Choose Warehouse',
    width    : 666,

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
            ,
            {
                xtype: 'panel',
                title: 'Welcome',
                // todo, load it from hardcoded json file
                html : 'Welcome at MajekWMS. Application has detected' +
                    ' that you have already defined some warehouses to be used' +
                    ' therefore you are allowed to pick one from the list on the' +
                    ' right side, and we can have some fun.'
            },
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