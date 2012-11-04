/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Invoice
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Invoice', {
    extend      : 'WMS.model.abstract.DescribedSimple',
    fields      : [
        'id', 'invoiceNumber',
        { name: 'createdDate', type: 'date'},
        { name: 'dueDate', type: 'date'}
    ],
    associations: [
        {name: 'invoiceProducts', type: 'belongsTo', model: 'WMS.model.entity.InvoiceProduct'},
        {name: 'invoiceType', type: 'hasOne', model: 'WMS.model.entity.InvoiceType'},
        {name: 'invoiceClient', type: 'hasOne', model: 'WMS.model.entity.Client'}
    ],
    validations : [
        { name: 'length', field: 'invoiceNumber', min: 5, max: 30},
        { name: 'length', field: 'description', min: 1, max: 250}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/invoice'
    }
});