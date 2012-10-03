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
        'id',
        'name',
        'description',
        'fkUnitType',
        'fkWarehouse',
        { name: 'size', type: 'int', defaultValue: 0},
        { name: 'maximumSize', type: 'int', defaultValue: 0}
    ],
    associations: [
        {
            name      : 'unitType',
            type      : 'hasOne',
            model     : 'WMS.model.entity.UnitType',
            getterName: 'getUnitType',
            setterName: 'setUnitType',
            foreignKey: 'fkUnitType'
        },
        {
            name : 'products',
            type : 'hasMany',
            model: 'WMS.model.entity.Product'
        }
    ],

    //TODO validations

    proxy: {
        type: 'wms',
        url : 'wms/agent/unit'
    }
});