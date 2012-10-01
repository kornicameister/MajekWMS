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
        { name: 'name', type: 'string', defaultValue: '', persist: true},
        { name: 'maximumSize', type: 'int', defaultValue: 0, persist: true},
        { name: 'size', type: 'int', defaultValue: 0, persist: false},
        { name: 'description', type: 'string', defaultValue: 'Warehouse...'},
        { name: 'createdDate', type: 'date'}
    ],
    associations: [
        {name: 'units', type: 'hasMany', model: 'entity.Unit'}
    ],
    validations : [
        { name: 'length', field: 'name', min: 5, max: 20},
        { name: 'length', field: 'description', min: 1, max: 666}
    ]

});