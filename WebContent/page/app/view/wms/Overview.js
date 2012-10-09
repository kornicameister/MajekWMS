/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 04:36
 * File   : Warehouse
 * Package: view.wms
 * Created: 14-09-2012
 */

Ext.define('WMS.view.wms.Overview', {
    extend : 'Ext.panel.Panel',
    alias  : 'widget.wmsoverviews',
    iconCls: 'view-wms-overview',

    layout: {
        type : 'hbox',
        align: 'stretch',
        pack : 'center'
    },

    items: [
        {
            xtype : 'panel',
            flex  : 1,
            layout: {
                type: 'fit'
            },
            itemId: 'warehouseDescription',
            title : 'Warehouse'
        },
        {
            xtype: 'splitter'
        },
        {
            xtype      : 'grid',
            itemId     : 'unitsGrid',
            store      : 'Units',
            emptyText  : 'No units for selected warehouse',
            flex       : 3,
            columnWidth: 120,
            selModel   : {
                xtype: 'rowmodel',
                mode : 'MULTI'
            },
            columns    : [
                {
                    header   : 'ID',
                    dataIndex: 'id',
                    editable : false,
                    width    : 20
                },
                {
                    header   : 'Name',
                    dataIndex: 'name',
                    field    : {
                        xtype     : 'textfield',
                        name      : 'name',
                        allowBlank: false
                    }
                },
                {
                    header   : 'Size',
                    dataIndex: 'size',
                    field    : {
                        xtype   : 'numberfield',
                        enabled : false,
                        editable: false,
                        value   : 0
                    }
                },
                {
                    header   : 'Maximum size',
                    dataIndex: 'maximumSize',
                    field    : {
                        xtype            : 'numberfield',
                        name             : 'maximumSize',
                        allowBlank       : false,
                        value            : 100,
                        minValue         : 1,
                        maxValue         : Number.MAX_VALUE,
                        step             : 200,
                        keyNavEnabled    : true,
                        mouseWheelEnabled: true
                    }
                },
                {
                    header   : 'Type',
                    dataIndex: 'type',
                    editor   : {
                        xtype       : 'combo',
                        store       : Ext.create('WMS.store.UnitTypes', {
                            storeId    : 'UnitTypes',
                            autoDestroy: true
                        }),
                        valueField  : 'id',
                        displayField: 'name',

                        typeAhead    : true,
                        triggerAction: 'all',
                        selectOnTab  : true,
                        lazyRender   : true,
                        listClass    : 'x-combo-list-small',

                        allowBlank: false
                    }
                },
                {
                    header   : 'Description',
                    dataIndex: 'description',
                    flex     : 3,
                    field    : {
                        xtype     : 'textarea',
                        name      : 'description',
                        allowBlank: false,
                        maxLength : 250
                    }
                }
            ],
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
                            itemId  : 'delete',
                            text    : 'Delete',
                            iconCls : 'icon-delete',
                            disabled: true
                        }
                    ]
                }
            ],
            plugins    : [
                Ext.create('Ext.grid.plugin.RowEditing', {
                    pluginId: 'unitRowEditor'
                })
            ]
        }
    ]
});
