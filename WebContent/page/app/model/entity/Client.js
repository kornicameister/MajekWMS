/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:22
 */

Ext.define('WMS.model.entity.Client', {
    extend      : 'Ext.data.Model',
    requires    : [
        'WMS.model.entity.Address',
        'WMS.model.entity.ClientDetails'
    ],
    fields      : [
        'id',
        'name',
        'company',
        'description',
        { name: 'details_id', type: 'int', mapping: 'details.id'},
        { name: 'address_id', type: 'int', mapping: 'address.id'}
    ],
    associations: [
        {
            associationName: 'address',
            associationKey : 'address',
            instanceName   : 'address',
            foreignKey     : 'address_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.Address',
            getterName     : 'getAddress',
            setterName     : 'setAddress'
        },
        {
            associationName: 'details',
            associationKey : 'details',
            instanceName   : 'details',
            foreignKey     : 'details_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.ClientDetails',
            getterName     : 'getDetails',
            setterName     : 'setDetails'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/client'
    }
});