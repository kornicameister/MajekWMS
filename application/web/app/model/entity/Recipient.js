/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 01:26
 */

Ext.define('WMS.model.entity.Recipient', {
    extend: 'WMS.model.entity.Client',
    proxy : {
        type       : 'wms',
        url        : 'wms/agent/client',
        extraParams: {
            type: 'recipient'
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