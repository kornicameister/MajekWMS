/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Warehouse
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Warehouse', {
    extend: 'Ext.data.Model',

    fields      : [
        'id',
        'name',
        'description',
        { name: 'maximumSize', type: 'int', defaultValue: 0},
        { name: 'size', type: 'int', defaultValue: 0},
        { name: 'createdDate', type: 'date', serialize: serializeDate}
    ],
    associations: [
        {name: 'units', type: 'hasMany', model: 'WMS.model.entity.Unit'}
    ],

    proxy: {
        type: 'wms',
        url : 'wms/agent/warehouse'
    }
});

function serializeDate(value) {
    return Ext.Date.format(new Date(value), 'Y-m-d');
}