/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:50
 */

Ext.define('WMS.view.wms.unit.Inventory', {
    extend : 'WMS.view.abstract.EditableGrid',
    alias  : 'widget.wmsunitinventory',
    iconCls: 'view-wms-inventory',

    title     : 'Inventory',
    itemId    : 'inventoryGrid',
    emptyText : 'No products for selected unit...',
    autoScroll: true,

    features: [
        { ftype: 'summary' }
    ],

    columns: [
        {
            header   : 'ID',
            dataIndex: 'id',
            width    : 20,
            editable : false,
            hidden   : true
        },
        {
            header         : 'Nazwa',
            dataIndex      : 'name',
            summaryType    : 'count',
            summaryRenderer: function (value) {
                return Ext.String.format('{0} produkt{1}', value, value !== 1 ? 'ów' : '');
            },
            field          : {
                xtype     : 'textfield',
                name      : 'name',
                allowBlank: false
            }
        },
        {
            header         : 'PJŁ',
            dataIndex      : 'pallets',
            summaryType    : 'sum',
            sortable       : true,
            summaryType    : 'sum',
            summaryRenderer: function (value) {
                var total = Ext.getStore('Units').getActive().get('size');
                return Ext.String.format('{0} z {1} PJŁ', value, total);
            },
            field          : {
                xtype     : 'navnumberfield',
                name      : 'pallets',
                allowBlank: false,
                value     : 1,
                minValue  : 1,
                maxValue  : Number.MAX_VALUE,
                step      : 1
            }
        },
        {
            header     : 'Ilość',
            dataIndex  : 'quantity',
            summaryType: 'average',
            field      : {
                xtype     : 'navnumberfield',
                name      : 'maximumSize',
                allowBlank: false,
                value     : 100,
                minValue  : 1,
                maxValue  : Number.MAX_VALUE,
                step      : 200
            }
        },
        {
            header   : 'Jednostka',
            dataIndex: 'measure_id',
            width    : 120,
            renderer : measureColumnRenderer,
            editor   : {
                xtype       : 'combo',
                store       : 'Measures',
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
            header         : 'Cena',
            dataIndex      : 'price',
            summaryType    : 'average',
            summaryRenderer: function (value) {
                return value + ' zł';
            },
            field          : {
                xtype     : 'navnumberfield',
                name      : 'price',
                allowBlank: false,
                value     : 22,
                minValue  : 0.1,
                maxValue  : Number.MAX_VALUE,
                step      : 0.1
            }
        },
        {
            header   : 'Podatek',
            dataIndex: 'tax',
            renderer : function (value) {
                return value + ' %';
            },
            field    : {
                xtype     : 'navnumberfield',
                name      : 'tax',
                allowBlank: false,
                value     : 22,
                minValue  : 10,
                maxValue  : 100,
                step      : 0.5
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
            pluginId: 'inventoryRowEditor'
        })
    ]
});

function measureColumnRenderer(measure_id) {

    if (!Ext.isNumber(measure_id)) {
        return 'unknown';
    } else if (Ext.isString(measure_id)) {
        measure_id = parseInt(measure_id);
    }

    if (measure_id > 0) {
        return Ext.getStore('Measures').getById(measure_id).get('abbreviation');
    } else if (measure_id === 0) {
        return 'undefined';
    }

    console.error("InventoryView :: Failed to recognize measure_id");
    return '';
}