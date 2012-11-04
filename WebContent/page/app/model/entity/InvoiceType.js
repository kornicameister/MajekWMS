/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 03.10.12
 * Time   : 09:23
 */

Ext.define('WMS.model.entity.InvoiceType', {
    extend      : 'Ext.data.Model',
    fields      : [
        'id', 'name'
    ],
    associations: [
        {name: 'invoice', type: 'belongsTo', model: 'WMS.model.entity.Invoice'}
    ],
    validations : [
        { name: 'length', field: 'invoiceNumber', min: 5, max: 30},
        { name: 'length', field: 'description', min: 1, max: 250}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/invoicetype'
    }
});