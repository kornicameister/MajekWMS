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
    uses   : [
        'WMS.view.abstract.EditableGrid',
        'WMS.view.abstract.NavigableNumberField'
    ],
    alias  : 'widget.wmsoverviews',
    iconCls: 'view-wms-overview',
    layout : {
        type : 'hbox',
        align: 'stretch',
        pack : 'center'
    },
    items  : [
        {
            xtype : 'propertygrid',
            flex  : 1,
            itemId: 'description',
            title : 'Warehouse',
            source: {}
        },
        {
            xtype    : 'egrid',
            itemId   : 'unitsGrid',
            emptyText: 'W tym magazynie nie ma jeszcze żadnej strefy...',
            flex     : 3,

            features: [
                {
                    id                : 'group',
                    ftype             : 'groupingsummary',
                    groupHeaderTpl    : '{name}',
                    hideGroupedHeader : true,
                    enableGroupingMenu: false
                }
            ],

            columns: [
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
                    header   : 'Wypełnienie',
                    dataIndex: 'usage',
                    readOnly : true,
                    renderer : sizeColumnRenderer
                },
                {
                    header   : 'Maksymalny rozmiar',
                    dataIndex: 'size',
                    field    : {
                        xtype     : 'navnumberfield',
                        name      : 'maximumSize',
                        allowBlank: false,
                        value     : 100,
                        minValue  : 1,
                        maxValue  : Number.MAX_VALUE,
                        step      : 50
                    },
                    flex     : 2
                },
                {
                    header   : 'Typ',
                    dataIndex: 'unittype_id',
                    flex     : 2,
                    renderer : typeColumnRenderer,
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
            plugins: [
                Ext.create('Ext.grid.plugin.RowEditing', {
                    pluginId: 'unitRowEditor'
                })
            ]
        }
    ]
});

function sizeColumnRenderer(value) {
    value *= 100;
    return value.toFixed(2) + ' %';
}

function typeColumnRenderer(unittype_id) {
    if (!Ext.isNumber(unittype_id)) {
        return 'unknown';
    } else if (Ext.isString(unittype_id)) {
        unittype_id = parseInt(unittype_id);
    }

    if (unittype_id > 0) {
        return Ext.getStore('UnitTypes').getById(unittype_id).get('name');
    } else if (unittype_id === 0) {
        return 'undefined';
    }

    console.error("OverviewView :: Failed to recognize unittype_id");
    return '';
}
