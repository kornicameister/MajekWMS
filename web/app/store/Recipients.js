/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:24
 */

Ext.define('WMS.store.Recipients', {
    extend              : 'Ext.data.Store',
    model               : 'WMS.model.entity.Recipient',
    autoLoad            : true,
    autoSync            : true,
    saveAssociatedClient: function (rawClient) {
        var me = this,
            address = WMS.model.entity.Address.extract(rawClient),
            client = WMS.model.entity.Client.extract(rawClient),
            clientDetails = WMS.model.entity.ClientDetails.extract(rawClient);

        client = Ext.create('WMS.model.entity.Recipient', client);
        client['address'] = address;
        client['details'] = clientDetails;
        client['type'] = {id: "recipient"};

        me.add(client);
    }
});
