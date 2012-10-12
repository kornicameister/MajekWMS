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
            itemId: 'description',
            title : 'Warehouse'
        },
        {
            xtype: 'splitter'
        },
        {
            xtype      : 'grid',
            itemId     : 'unitsGrid',
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
                    dataIndex: 'unittype_id',
                    renderer : function (unittype_id) {

                        if (!Ext.isNumber(unittype_id)) {
                            return 'unknown';
                        } else if (Ext.isString(unittype_id)) {
                            unittype_id = parseInt(unittype_id);
                        }

                        if (unittype_id > 0) {
                            return Ext.getStore('UnitTypes').getById(unittype_id).get('name')
                        } else if (unittype_id === 0) {
                            return 'undefined';
                        }

                        console.error("OverviewView :: Failed to recognize unittype_id");
                        return '';
                    },
                    editor   : {
                        xtype       : 'combo',
                        store       : 'UnitTypes',
                        valueField  : 'id',
                        displayField: 'name',

                        width: 200,

                        typeAhead    : true,
                        triggerAction: 'all',
                        selectOnTab  : true,
                        lazyRender   : true,
                        listClass    : 'x-combo-list-small',

                        tpl       : Ext.create('Ext.XTemplate',
                            '<tpl for=".">',
                            '<div class="x-boundlist-item">{abbreviation} - {description}</div>',
                            '</tpl>'
                        ),
                        displayTpl: Ext.create('Ext.XTemplate',
                            '<tpl for=".">',
                            '{abbreviation} - {name}',
                            '</tpl>'
                        ),

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
