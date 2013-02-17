/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 15:02
 */

Ext.define('WMS.model.entity.ClientDetails', {
    extend      : 'Ext.data.Model',
    fields      : [
        'id',
        'nip',
        'phone',
        'account'
    ],
    associations: [
        {
            name : 'client',
            type : 'belongsTo',
            model: 'WMS.model.entity.Client'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/clientDetails'
    },
    statics     : {
        extract: function (raw) {
            var fields = [ 'nip', 'phone', 'fax', 'account'],
                clientDetails = {};
            Ext.each(fields, function (chunk) {
                clientDetails[chunk] = raw[chunk];
            });
            return clientDetails;
        }
    }
});