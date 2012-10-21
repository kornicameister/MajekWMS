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
        { name: 'id', persist: true},
        { name: 'name'},
        { name: 'description'},
        { name: 'unit_id', type: 'int'},
        { name: 'measure_id', type: 'int', mapping: 'measure.id'},
        { name: 'quantity', type: 'float', defaultValue: 0.0},
        { name: 'price', type: 'float', defaultValue: 0.0},
        { name: 'tax', type: 'int', defaultValue: 22}
    ],
    associations: [
        {
            name          : 'unit',
            type          : 'belongsTo',
            model         : 'WMS.model.entity.Unit',
            setterName    : 'setUnit',
            getterName    : 'getUnit',
            associationKey: 'unit_id'
        },
        {
            name : 'invoiceProducts',
            type : 'hasMany',
            model: 'WMS.model.entity.InvoiceProduct'
        },
        {
            associationName: 'measure',
            type           : 'hasOne',
            model          : 'WMS.model.entity.Measure',
            setterName     : 'setMeasure',
            getterName     : 'getMeasure'
        }
    ],

    // TODO validations
    sorters     : [
        'id'
    ],

    proxy: {
        type    : 'wms',
        url     : 'wms/agent/product'
    }
});