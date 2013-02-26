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
    extend      : 'WMS.model.abstract.DescribedSimple',
    fields      : [
        { name: 'unit_id', type: 'int'},
        { name: 'measure_id', type: 'int', mapping: 'measure.id'},
        { name: 'price', type: 'float', defaultValue: 0.0},
        { name: 'tax', type: 'int', defaultValue: 22},
        { name: 'pallets', type: 'int'},
        { name: 'totalCount', type: 'int', persist: false}
    ],
    associations: [
        {
            associationName: 'measure',
            type           : 'hasOne',
            model          : 'WMS.model.entity.Measure',
            setterName     : 'setMeasure',
            getterName     : 'getMeasure'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/product'
    }
});