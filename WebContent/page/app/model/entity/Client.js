/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:22
 */

Ext.define('WMS.model.entity.Client', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'id', type: 'int' },
        { name: 'name', type: 'string'},
        { name: 'company', type: 'string'},
        { name: 'description', type: 'string'}
    ],

    associations: [
        {
            type      : 'hasMany',
            foreignKey: 'client_id',
            name      : 'getClients',
            model     : 'WMS.model.entity.Invoice'
        },
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.ClientDetails',
            associatedName: 'details',
            getterName    : 'getDetails',
            setterName    : 'setDetails'
        }
    ],

    proxy: {
        type: 'wms',
        url : 'wms/agent/client'
    }

});