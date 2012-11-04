/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 03.10.12
 * Time   : 09:23
 */

Ext.define('WMS.model.entity.InvoiceType', {
    extend      : 'WMS.model.abstract.Simple',
    associations: [
        {name: 'invoice', type: 'belongsTo', model: 'WMS.model.entity.Invoice'}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/invoicetype'
    }
});