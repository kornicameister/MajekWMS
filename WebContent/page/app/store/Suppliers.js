/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:02
 */

Ext.define('WMS.store.Suppliers', {
    extend              : 'Ext.data.Store',
    model               : 'WMS.model.entity.Supplier',
    autoLoad            : true,
    autoSync            : true,
    saveAssociatedClient: function (rawClient) {
        var me = this,
            address = WMS.model.entity.Address.extract(rawClient),
            client = WMS.model.entity.Client.extract(rawClient),
            clientDetails = WMS.model.entity.ClientDetails.extract(rawClient);

        client = Ext.create('WMS.model.entity.Supplier', client);
        client['address'] = address;
        client['details'] = clientDetails;
        client['type'] = {id: 1};

        me.add(client);
    }
});