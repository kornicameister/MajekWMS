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
        {name: 'invoiceProducts', type: 'belongsTo', model: 'WMS.model.entity.InvoiceProduct'}
    ],
    validations : [
        { name: 'length', field: 'name', min: 5, max: 45},
        { name: 'length', field: 'description', min: 1, max: 250}
    ],

    proxy: {
        type     : 'rest',
        api      : {
            read  : 'wms/agent/invoice/read',
            update: 'wms/agent/invoice/update'
        },
        reader   : {
            type           : 'json',
            root           : 'warehouses',
            successProperty: 'success'
        },
        writer   : {
            type          : 'json',
            writeAllFields: false,
            root          : 'warehouses'
        },
        listeners: {
            exception: function (proxy, response, operation) {
                Ext.MessageBox.show({
                    title  : 'REMOTE EXCEPTION',
                    msg    : operation.getError(),
                    icon   : Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    }

});