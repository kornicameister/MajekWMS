/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:41
 */

Ext.define('WMS.model.entity.Company', {
    extend      : 'WMS.model.abstract.Simple',
    fields      : [
        { name: 'longname', type: 'string' },
        { name: 'warehouse_id', type: 'int', mapping: 'warehouse.id'}
    ],
    associations: [
        {
            type           : 'hasOne',
            associationName: 'warehouse',
            instanceName   : 'warehouse',
            foreignKey     : 'warehouse_id',
            model          : 'WMS.model.entity.Warehouse',
            getterName     : 'setWarehouse',
            setterName     : 'getWarehouse'
        }
    ],
    validations : [
        {type: 'length', field: 'longname', max: 45, min: 10},
        {type: 'length', field: 'name', min: 2, max: 10}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/company'
    }
});