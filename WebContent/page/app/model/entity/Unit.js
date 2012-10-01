/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Unit
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Unit', {
    extend: 'Ext.data.Model',

    fields      : [
        { name: 'name', type: 'string', defaultValue: '', persist: true},
        { name: 'size', type: 'int', defaultValue: 0},
        { name: 'maxSize', type: 'int', defaultValue: 0, persist: true},
        { name: 'description', type: 'string', defaultValue: 'Unit...'}
    ],
    associations: [
        {name: 'type', type: 'hasOne', model: 'entity.UnitType'},
        {name: 'warehouse', type: 'belongsTo', model: 'entity.Warehouse'},
        {name: 'products', type: 'hasMany', model: 'entity.Product'}
    ],
    validations : [
        { name: 'length', field: 'name', min: 5, max: 45},
        { name: 'length', field: 'description', min: 1, max: 250}
    ]

});