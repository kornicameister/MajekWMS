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
        { name: 'idWarehouse', type: 'int', defaultValue: -1, mapping: 'id'},
        { name: 'name', type: 'string', defaultValue: '', persist: true},
        { name: 'maximumSize', type: 'int', defaultValue: 0, persist: true},
        { name: 'size', type: 'int', defaultValue: 0, persist: false},
        { name: 'description', type: 'string', defaultValue: 'Warehouse...'},
        { name: 'createdDate', type: 'date', serialize: serializeDate}
    ],
    associations: [
        {name: 'units', type: 'hasMany', model: 'WMS.model.entity.Unit'}
    ],

    proxy: {
        type     : 'rest',
        api      : {
            read  : 'wms/agent/warehouse/read',
            create: 'wms/agent/warehouse/create'
        },
        reader   : {
            type           : 'json',
            root           : 'warehouses',
            successProperty: 'success'
        },
        writer   : {
            type       : 'json',
            root       : 'warehouses',
            allowSingle: false
        },
        listeners: {
            exception: function (proxy, response, operation) {
                Ext.MessageBox.show({
                    title  : 'REMOTE EXCEPTION',
                    msg    : operation.getError(),
                    icon   : Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    }
});

function serializeDate(value) {
    return Ext.Date.format(new Date(value), 'Y-m-d');
}