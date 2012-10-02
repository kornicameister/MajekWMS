/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 30.09.12
 * Time   : 21:45
 */

Ext.define('WMS.model.entity.UnitTypeSimple', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'int', persist: true, mapping: 'idUnitType'},
        { name: 'name', type: 'string', persist: true},
        { name: 'abbreviation', type: 'string', persist: true}
    ],

    proxy: {
        type      : 'rest',
        api       : {
            read  : 'wms/agent/unittype/read',
            update: 'wms/agent/unittype/update'
        },
        reader    : {
            type           : 'json',
            root           : 'unitTypes',
            successProperty: 'success'
        },
        limitParam: 'simple',
        listeners : {
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