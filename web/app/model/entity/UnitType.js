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
    extend     : 'WMS.model.abstract.DescribedSimple',
    fields     : [
        'abbreviation',
        'parentType'
    ],
    validations: [
        { name: 'length', field: 'name', min: 5, max: 20},
        { name: 'length', field: 'abbreviation', min: 1, max: 6},
        { name: 'length', field: 'description', min: 1, max: 120}
    ],
    proxy      : {
        type: 'wms',
        url : 'wms/agent/unitType'
    }
});