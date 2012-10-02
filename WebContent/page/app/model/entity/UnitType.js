/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : UnitType
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.UnitType', {
    extend: 'Ext.data.Model',

    fields      : [
        { name: 'name', type: 'string', defaultValue: '', persist: true},
        { name: 'abbreviation', type: 'string', defaultValue: '', persist: true},
        { name: 'description', type: 'string', defaultValue: 'Warehouse...'},
        { name: 'createdDate', type: 'date'},
        { name: 'parentType'}
    ],
    associations: [
        {name: 'unitType', type: 'belongsTo', model: 'WMS.model.entity.Unit'}
    ],
    validations : [
        { name: 'length', field: 'name', min: 5, max: 20},
        { name: 'length', field: 'abbreviation', min: 1, max: 6},
        { name: 'length', field: 'description', min: 1, max: 120}
    ],

    proxy: {
        type     : 'rest',
        api      : {
            read  : 'wms/agent/unittype/read',
            update: 'wms/agent/unittype/update'
        },
        reader   : {
            type           : 'json',
            root           : 'warehouses',
            successProperty: 'success'
        },
        writer   : {
            type          : 'json',
            writeAllFields: false,
            root          : 'warehouses'
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