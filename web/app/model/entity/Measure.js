/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 08:46
 */

Ext.define('WMS.model.entity.Measure', {
    extend      : 'WMS.model.abstract.Simple',
    fields      : [
        'abbreviation'
    ],
    associations: [
        {name: 'products', type: 'belongsTo', model: 'WMS.model.entity.Product'}
    ],
    validations : [
        { name: 'length', field: 'name', min: 5, max: 45},
        { name: 'length', field: 'abbreviation', min: 1, max: 45}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/measure'
    }
});