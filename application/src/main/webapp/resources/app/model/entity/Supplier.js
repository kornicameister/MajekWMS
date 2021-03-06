/**
 * Created with JetBrains WebStorm.
 * User: kornicameister
 * Date: 04.11.12
 * Time: 01:25
 * To change this template use File | Settings | File Templates.
 */

Ext.define('WMS.model.entity.Supplier', {
    extend: 'WMS.model.entity.Client',
    proxy : {
        type       : 'wms',
        url        : 'wms/agent/client',
        extraParams: {
            type: 'supplier'
        },
        writer     : {
            type          : 'json',
            root          : 'data',
            allowSingle   : false,
            writeAllFields: false,
            getRecordData : function (record, operation) {
                return WMS.model.entity.Client.getRecordData(record, operation);
            }
        }
    }
});