/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:38
 */

Ext.define('WMS.model.entity.Receipt', {
    extend: 'WMS.model.entity.Invoice',
    proxy : {
        type       : 'wms',
        url        : 'wms/agent/invoice',
        extraParams: {
            type: 'receipt'
        }
    }
});