/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 01:26
 */

Ext.define('WMS.model.entity.client.Recipient', {
    extend: 'WMS.model.entity.Client',
    proxy : {
        type       : 'wms',
        url        : 'wms/agent/client',
        extraParams: {
            type: 'recipient'
        }
    }
});