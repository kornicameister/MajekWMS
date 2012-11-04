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
        'WMS.model.entity.ClientDetails',
        'WMS.model.entity.ClientType'
    ],
    fields      : [
        'id',
        'name',
        'company',
        'description',
        { name: 'details_id', type: 'int', mapping: 'details.id'},
        { name: 'address_id', type: 'int', mapping: 'address.id'},
        { name: 'type_id', type: 'int', mapping: 'type.id'}
    ],
    associations: [
        {
            associationName: 'address',
            associationKey : 'address',
            instanceName   : 'address',
            name           : 'address',
            foreignKey     : 'address_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.Address',
            getterName     : 'getAddress',
            setterName     : 'setAddress'
        },
        {
            associationName: 'type',
            associationKey : 'type',
            instanceName   : 'type',
            name           : 'type',
            foreignKey     : 'type_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.ClientType',
            getterName     : 'getType',
            setterName     : 'setType'
        },
        {
            associationName: 'details',
            associationKey : 'details',
            instanceName   : 'details',
            name           : 'details',
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