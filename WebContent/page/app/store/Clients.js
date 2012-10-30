/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:20
 */

Ext.define('WMS.store.Clients', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Client',

    autoLoad: true,
    autoSync: true,

    add: function (records) {
        var me = this,
            recordsM = [];

        Ext.each(records, function (client) {
            recordsM.push(client);
        });
        return recordsM;
    }
});