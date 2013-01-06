/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:39
 */

Ext.define('WMS.model.entity.Supply', {
    extend: 'WMS.model.entity.Invoice',
    proxy : {
        type       : 'wms',
        url        : 'wms/agent/invoice',
        extraParams: {
            type: 'supply'
        }
    }
});