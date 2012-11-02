/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 14:58
 */

Ext.define('WMS.model.entity.Address', {
    extend      : 'Ext.data.Model',
    requires    : [
        'WMS.model.entity.City'
    ],
    fields      : [
        'id',
        'postcode',
        'street',
        { name: 'city_id', type: 'int', mapping: 'city.id'}
    ],
    associations: [
        {
            name : 'client',
            type : 'belongsTo',
            model: 'WMS.model.entity.Client'
        },
        {
            associationName: 'city',
            associationKey : 'city',
            instanceName   : 'city',
            foreignKey     : 'city_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.City',
            setterName     : 'setCity',
            getterName     : 'getCity'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/address'
    }
});