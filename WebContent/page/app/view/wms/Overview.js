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
            title : 'Warehouse',
            tpl   : new Ext.XTemplate(
                // TODO support for dd,dt and so on
                '<div class="warehouseDescription">',
                '<p>Name: {name}</p>',
                '<p>Description: {description}</p>',
                '<p>Usage: {size} of {maximumSize}</p>',
                '<p>Created: {createdDate}</p>',
                '</div>'
            )
        },
        {
            xtype: 'splitter'
        },
        {
            xtype      : 'egrid',
            itemId     : 'unitsGrid',
            emptyText  : 'No units for selected warehouse',
            flex       : 3,
            columns    : [
                {
                    header   : 'ID',
                    dataIndex: 'id',
                    editable : false,
                    width    : 20
                },
                {
                    header   : 'Nazwa',
                    dataIndex: 'name',
                    field    : {
                        xtype     : 'textfield',
                        name      : 'name',
                        allowBlank: false
                    }
                },
                {
                    header   : 'Rozmiar',
                    dataIndex: 'size',
                    readOnly : true
                },
                {
                    header   : 'Maksymalny rozmiar',
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
                    header   : 'Typ',
                    dataIndex: 'unittype_id',
                    flex     : 2,
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
                            '<div class="x-boundlist-item">{name} - {description}</div>',
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
                    header   : 'Opis',
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
            plugins    : [
                Ext.create('Ext.grid.plugin.RowEditing', {
                    pluginId: 'unitRowEditor'
                })
            ]
        }
    ]
});
