/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 24.12.12
 * Time     : 22:02
 */

Ext.define('WMS.model.entity.InvoiceProductPK', {
    extend      : 'Ext.data.Model',
    requires    : [
        'WMS.model.entity.Invoice',
        'WMS.model.entity.Product'
    ],
    fields      : [
        { name: 'product_id', type: 'int', mapping: 'product.id'},
        { name: 'invoice_id', type: 'int', mapping: 'invoice.id'}
    ],
    associations: [
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.Invoice',
            associationKey: 'invoice',
            associatedName: 'invoice',
            instanceName  : 'invoice',
            name          : 'invoice',
            getterName    : 'getInvoice',
            setterName    : 'setInvoice',
            foreignKey    : 'invoice_id'
        },
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.Product',
            associationKey: 'product',
            associatedName: 'product',
            instanceName  : 'product',
            name          : 'product',
            getterName    : 'getProduct',
            setterName    : 'setProduct',
            foreignKey    : 'product_id'
        }
    ]
});