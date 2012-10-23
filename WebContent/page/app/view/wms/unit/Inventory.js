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
    columns   : [
        {
            header   : 'ID',
            dataIndex: 'id',
            width    : 20,
            editable : false
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
            header   : 'Ilość',
            dataIndex: 'quantity',
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
            header   : 'Jednostka',
            dataIndex: 'measure_id',
            width    : 120,
            renderer : function (measure_id) {

                if (!Ext.isNumber(measure_id)) {
                    return 'unknown';
                } else if (Ext.isString(measure_id)) {
                    measure_id = parseInt(measure_id);
                }

                if (measure_id > 0) {
                    return Ext.getStore('Measures').getById(measure_id).get('abbreviation')
                } else if (measure_id === 0) {
                    return 'undefined';
                }

                console.error("InventoryView :: Failed to recognize measure_id");
                return '';
            },
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
            header   : 'Cena',
            dataIndex: 'price',
            field    : {
                xtype            : 'numberfield',
                name             : 'price',
                allowBlank       : false,
                value            : 22,
                minValue         : 0.1,
                maxValue         : Number.MAX_VALUE,
                step             : 0.1,
                keyNavEnabled    : true,
                mouseWheelEnabled: true
            }
        },
        {
            header   : 'Podatek',
            dataIndex: 'tax',
            renderer : function (value) {
                return value + ' %';
            },
            field    : {
                xtype            : 'numberfield',
                name             : 'tax',
                allowBlank       : false,
                value            : 22,
                minValue         : 10,
                maxValue         : 100,
                step             : 0.5,
                keyNavEnabled    : true,
                mouseWheelEnabled: true
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
    plugins   : [
        Ext.create('Ext.grid.plugin.RowEditing', {
            pluginId: 'inventoryRowEditor'
        })
    ]
});