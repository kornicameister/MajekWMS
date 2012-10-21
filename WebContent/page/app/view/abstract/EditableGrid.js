/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 12:35
 */

Ext.define('WMS.view.abstract.EditableGrid', {
    extend     : 'Ext.grid.Panel',
    alias      : 'widget.egrid',
    columnWidth: 120,
    selModel   : {
        xtype: 'rowmodel',
        mode : 'MULTI'
    },
    dockedItems: [
        {
            xtype   : 'toolbar',
            layout  : {
                type: 'hbox',
                pack: 'center'
            },
            defaults: {
                width: 150
            },
            items   : [
                {
                    text   : 'Add',
                    itemId : 'add',
                    iconCls: 'icon-add'
                },
                {
                    text    : 'Delete',
                    itemId  : 'delete',
                    iconCls : 'icon-delete',
                    disabled: true
                }
            ]
        }
    ]
});