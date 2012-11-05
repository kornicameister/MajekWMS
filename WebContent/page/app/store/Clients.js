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

    /**
     * This method saves new client to server along with associated data
     * such as details and address.
     * Client is valid only if address and details are valid. But even though
     * address can be saved prior to client there is a problem with
     * clientDetails that must be saved after client's successful save;
     * @param rawClient
     */
    saveAssociatedClient: function (rawClient) {
        var address = WMS.model.entity.Address.extract(rawClient),
            client = WMS.model.entity.Client.extract(rawClient),
            clientDetails = WMS.model.entity.ClientDetails.extract(rawClient);

        client = Ext.create('WMS.model.entity.Client', client);
        client['address'] = address;
        client['details'] = clientDetails;
        client['type'] = {id: 1};

        this.add(client);
    }
});