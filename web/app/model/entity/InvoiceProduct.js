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
    requires    : [
        'WMS.model.entity.InvoiceProductPK'
    ],
    fields      : [
        'id',
        'comment',
        { name: 'quantity', type: 'float', defaultValue: 0.0},
        { name: 'price', type: 'float', defaultValue: 0.0},
        { name: 'tax', type: 'int', defaultValue: 22},
        { name: 'summaryPrice', type: 'float', persist: false},
        { name: 'invoiceProduct_id', type: 'int' },
        { name: 'product_id', type: 'int', persist: false }
    ],
    associations: [
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.InvoiceProductPK',
            associationKey: 'invoiceProduct',
            associatedName: 'invoiceProduct',
            instanceName  : 'invoiceProduct',
            name          : 'invoiceProduct',
            getterName    : 'getInvoiceProduct',
            setterName    : 'setInvoiceProduct',
            foreignKey    : 'invoiceProduct_id'
        }
    ],
    proxy       : {
        type  : 'wms',
        url   : 'wms/agent/invoiceProduct',
        writer: {
            type          : 'json',
            root          : 'data',
            allowSingle   : false,
            writeAllFields: false,
            getRecordData : function (record, operation) {
                var data;
                if (operation['action'] === 'create') {
                    var data = record.getData(),
                        invoiceProduct = record['invoiceProduct'];

                    data['invoiceProduct'] = invoiceProduct.getData();

                    delete data['invoiceProduct_id'];
                    delete data['invoiceProduct']['id'];
                } else {
                    data = record.getData();
                }
                return data;
            }
        }
    }
});