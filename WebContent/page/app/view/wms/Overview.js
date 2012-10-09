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
            plugins    : [
                Ext.create('Ext.grid.plugin.RowEditing', {
                    pluginId: 'unitRowEditor'
                })
            ],
            flex       : 3,
            store      : 'Units',
            columnWidth: 120,
            viewConfig : {
                forceFit: true
            },
            columns    : [
                {
                    header   : 'ID',
                    dataIndex: 'id',
                    hidden   : true,
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
                        xtype  : 'numberfield',
                        enabled: false,
                        value  : 0
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
                    header: 'Type',
                    field : {
                        xtype: 'combo',
                        name : 'type',

                        store       : Ext.create('WMS.store.UnitTypes', {
                            storeId    : 'UnitTypes',
                            autoDestroy: true
                        }),
                        valueField  : 'id',
                        displayField: 'name',

                        typeAhead     : true,
                        forceSelection: true,
                        allowBlank    : false
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
            ]
        }
    ]
});
