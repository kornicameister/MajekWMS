/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Product
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Product', {
    extend: 'Ext.data.Model',

    fields      : [
        'id', 'name', 'description',
        { name: 'quantity', type: 'float', defaultValue: 0.0},
        { name: 'price', type: 'float', defaultValue: 0.0},
        { name: 'tax', type: 'int', defaultValue: 22}
    ],
    associations: [
        {name: 'units', type: 'belongsTo', model: 'WMS.model.entity.Unit'},
        {name: 'invoiceProducts', type: 'belongsTo', model: 'WMS.model.entity.InvoiceProduct'},
        {name: 'measure', type: 'hasOne', model: 'WMS.model.entity.Measure'},
        {name: 'vendor', type: 'hasOne', model: 'WMS.model.entity.Client'}
    ],

    // TODO validations

    proxy: {
        type: 'wms',
        url : 'wms/agent/product'
    }
});