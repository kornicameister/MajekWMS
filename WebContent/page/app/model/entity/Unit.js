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

    requires: [
        'WMS.model.entity.Product'
    ],

    fields: [
        'name',
        'description',
        'unittype_id',
        'warehouse_id',
        { name: 'size', type: 'int', defaultValue: 0},
        { name: 'maximumSize', type: 'int', defaultValue: 0}
    ],

    associations: [
        {
            type          : 'belongsTo',
            associationKey: 'warehouse',
            model         : 'WMS.model.entity.Warehouse',
            getterName    : 'getWarehouse',
            setterName    : 'setWarehouse',
            foreignKey    : 'warehouse_id'
        },
        {
            type          : 'hasOne',
            associationKey: 'type',
            model         : 'WMS.model.entity.UnitType',
            getterName    : 'getType',
            setterName    : 'setType',
            foreignKey    : 'unittype_id'
        },
        {
            type          : 'hasMany',
            associationKey: 'products',
            model         : 'WMS.model.entity.Product'
        }
    ],

    sorters: [
        'id', 'name'
    ],
    //TODO validations

    proxy: {
        type: 'wms',
        url : 'wms/agent/unit'
    }
});