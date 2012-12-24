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
    extend      : 'Ext.data.Model',
    requires    : [
        'WMS.model.entity.InvoiceType',
        'WMS.model.entity.Client'
    ],
    fields      : [
        'id',
        'invoiceNumber',
        'description',
        { name: 'client_id', type: 'int'},
        { name: 'type_id', type: 'int'},
        { name: 'invoiceProduct_id', type: 'int'},
        { name: 'createdDate', type: 'date'},
        { name: 'dueDate', type: 'date'}
    ],
    associations: [
        {
            type          : 'belongsTo',
            associationKey: 'invoiceProduct',
            model         : 'WMS.model.entity.InvoiceProduct',
            getterName    : 'getInvoiceProduct',
            setterName    : 'setInvoiceProduct',
            foreignKey    : 'invoiceProduct_id'
        },
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.InvoiceType',
            associationKey: 'type',
            getterName    : 'getType',
            setterName    : 'setType',
            foreignKey    : 'type_id'
        },
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.Client',
            associationKey: 'client',
            setterName    : 'setClient',
            getterName    : 'getClient',
            foreignKey    : 'client_id'
        }
    ],
    validations : [
        { name: 'length', field: 'invoiceNumber', min: 1, max: 18},
        { name: 'length', field: 'description', min: 1, max: 250}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/invoice'
    }
});