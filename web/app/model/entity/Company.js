/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:41
 */

Ext.define('WMS.model.entity.Company', {
    extend      : 'WMS.model.abstract.Simple',
    requires    : [
        'WMS.model.entity.Warehouse'
    ],
    fields      : [
        { name: 'longName', type: 'string' },
        { name: 'warehouse_id', type: 'int', mapping: 'warehouse.id'}
    ],
    associations: [
        {
            type           : 'hasOne',
            name           : 'warehouse',
            associationName: 'warehouse',
            instanceName   : 'warehouse',
            foreignKey     : 'warehouse_id',
            model          : 'WMS.model.entity.Warehouse',
            getterName     : 'setWarehouse',
            setterName     : 'getWarehouse'
        }
    ],
    proxy       : {
        type  : 'wms',
        url   : 'wms/agent/company',
        writer: {
            type          : 'json',
            root          : 'data',
            allowSingle   : false,
            writeAllFields: false,
            getRecordData : function (record, operation) {
                var company;
                if (operation['action'] === 'create') {
                    var company = record.getData(),
                        warehouse = record['warehouse'];

                    company['warehouse'] = warehouse.getData();
                    var warehouseID = company['warehouse_id'];

                    if (Ext.isDefined(warehouseID) && warehouseID === 0) {
                        delete company['warehouse_id'];
                    }
                } else {
                    company = record.getData();
                }
                return company;
            }
        }
    }
});
