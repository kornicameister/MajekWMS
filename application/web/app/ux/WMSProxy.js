/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 03.10.12
 * Time   : 16:45
 */

/**
 * Resuable proxy that defines custom behaviour
 * for all entities of MajekWMS project
 */
Ext.define('Ext.ux.proxy.WMSProxy', {
    extend            : 'Ext.data.proxy.Rest',
    alternateClassName: 'Ext.data.WMSProxy',
    alias             : 'proxy.wms',
    reader            : {
        type           : 'json',
        root           : 'data',
        successProperty: function (data) {
            return data['success'] === true
        },
        totalProperty  : 'total',
        messageProperty: 'message'
    },
    writer            : {
        type          : 'json',
        root          : 'data',
        allowSingle   : false,
        writeAllFields: false
    },
    batchActions      : true,
    listeners         : {
        exception: function (proxy, response, operation) {
            Ext.MessageBox.show({
                title  : 'REMOTE EXCEPTION',
                msg    : operation.getError(),
                icon   : Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    }
});