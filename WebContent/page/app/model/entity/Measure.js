/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 08:46
 */

Ext.define('WMS.model.entity.Measure', {
    extend: 'Ext.data.Model',

    fields      : [
        { name: 'idMeasure', type: 'int', defaultValue: -1},
        { name: 'name', type: 'string', defaultValue: ''},
        { name: 'abbreviation', type: 'string', defaultValue: ''}
    ],
    associations: [
        {name: 'warehouse', type: 'belongsTo', model: 'WMS.model.entity.Product'}
    ],
    validations : [
        { name: 'length', field: 'name', min: 5, max: 45},
        { name: 'length', field: 'abbreviation', min: 1, max: 45}
    ]

});