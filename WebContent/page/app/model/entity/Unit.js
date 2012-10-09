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
            associatedName: 'type',
            type          : 'hasOne',
            model         : 'WMS.model.entity.UnitType',
            getterName    : 'getType',
            setterName    : 'setType',
            foreignKey    : 'fkUnitType'
        },
        {
            associatedName: 'warehouse',
            type          : 'belongsTo',
            model         : 'WMS.model.entity.Warehouse',
            getterName    : 'getWarehouse',
            setterName    : 'setWarehouse',
            foreignKey    : 'fkWarehouse'
        }
        // TODO add products mapping
    ],
    sorters     : [
        'id', 'name'
    ],
    //TODO validations

    proxy: {
        type: 'wms',
        url : 'wms/agent/unit'
    }
});