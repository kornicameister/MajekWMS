/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Invoice
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.InvoiceProduct', {
    extend      : 'Ext.data.Model',
    fields      : [
        'id', 'comment',
        { name: 'quantity', type: 'float', defaultValue: 0.0},
        { name: 'price', type: 'float', defaultValue: 0.0},
        { name: 'tax', type: 'int', defaultValue: 22}
    ],
    associations: [
        {name: 'invoice', type: 'hasOne', model: 'WMS.model.entity.Invoice'},
        {name: 'product', type: 'hasOne', model: 'WMS.model.entity.Product'}
    ],
    validations : [
        { name: 'length', field: 'comment', min: 1, max: 45}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/invoiceProduct'
    }
});