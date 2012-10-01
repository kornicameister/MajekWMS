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
    extend: 'Ext.data.Model',

    fields      : [
        { name: 'invoiceNumber', type: 'string'},
        { name: 'createdDate', type: 'date'},
        { name: 'dueDate', type: 'date'},
        { name: 'description', type: 'string', defaultValue: 'Unit...'}
    ],
    associations: [
        {name: 'warehouse', type: 'belongsTo', model: 'entity.Warehouse'},
        {name: 'products', type: 'hasMany', model: 'entity.Product'}
    ],
    validations : [
        { name: 'lenght', field: 'name', min: 5, max: 45},
        { name: 'lenght', field: 'description', min: 1, max: 250}
    ]

});