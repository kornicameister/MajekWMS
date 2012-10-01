/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 30.09.12
 * Time   : 23:38
 */

Ext.define('WMS.model.initial.Initial', {
    extend: 'Ext.data.Model',

    fields: [
        '_dc', 'warehouses', 'unitTypes'
    ],

    associations: [
        {name: 'warehouses', type: 'hasMany', model: 'entity.Warehouse'},
        {name: 'unitTypes', type: 'hasMany', model: 'entity.UnitTypeSimple'}
    ],

    proxy: {
        type       : 'rest',
        method     : 'POST',
        api        : {
            read  : 'wms/start/read',
            update: 'wms/start/update'
        },
        reader     : {
            type           : 'json',
            root           : 'configuration',
            successProperty: 'success'
        },
        writer     : {
            type          : 'json',
            writeAllFields: false,
            root          : 'configuration'
        },
        // params
        startParam : undefined,
        limitParam : undefined,
        pageParam  : undefined,
        extraParams: {
            requires: {
                property: ['warehouses', 'unitTypes', 'history']
            }
        },
        listeners  : {
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