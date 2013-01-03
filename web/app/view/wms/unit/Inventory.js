/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 08:50
 */

Ext.define('WMS.view.wms.unit.Inventory', {
    extend    : 'Ext.grid.Panel',
    alias     : 'widget.wmsunitinventory',
    iconCls   : 'view-wms-inventory',
    title     : 'Inventory',
    itemId    : 'inventoryGrid',
    emptyText : 'W tej strefie nie ma jeszcze żadnych produktów',
    autoScroll: true,
    features  : [
        {
            ftype: 'summary'
        }
    ],
    columns   : [
        {
            header   : 'ID',
            dataIndex: 'id',
            width    : 20,
            hidden   : true
        },
        {
            header         : 'Nazwa',
            dataIndex      : 'name',
            summaryType    : 'count',
            summaryRenderer: function (value) {
                return Ext.String.format('{0} produkt{1}', value, value !== 1 ? 'ów' : '');
            }
        },
        {
            header     : 'PJŁ',
            dataIndex  : 'pallets',
            summaryType: 'sum',
            sortable   : true
        },
        {
            header   : 'Jednostka',
            dataIndex: 'measure_id',
            width    : 120,
            renderer : measureColumnRenderer
        },
        {
            header         : 'Cena',
            dataIndex      : 'price',
            summaryType    : 'average',
            summaryRenderer: function (value) {
                return value + ' zł';
            }
        },
        {
            header   : 'Podatek',
            dataIndex: 'tax',
            renderer : function (value) {
                return value + ' %';
            }
        },
        {
            header   : 'Opis',
            dataIndex: 'description',
            flex     : 3
        }
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
