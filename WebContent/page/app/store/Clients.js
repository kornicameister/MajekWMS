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

    saveAssociatedClient: function (client) {
        this.add(client);
    }
});