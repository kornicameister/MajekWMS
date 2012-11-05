/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:22
 */

Ext.define('WMS.model.entity.Client', {
    extend      : 'WMS.model.abstract.DescribedSimple',
    requires    : [
        'WMS.model.entity.Address',
        'WMS.model.entity.ClientDetails',
        'WMS.model.entity.ClientType'
    ],
    fields      : [
        'company',
        { name: 'details_id', type: 'int', mapping: 'details.id'},
        { name: 'address_id', type: 'int', mapping: 'address.id'},
        { name: 'type_id', type: 'int', mapping: 'type.id'}
    ],
    associations: [
        {
            associationName: 'address',
            associationKey : 'address',
            instanceName   : 'address',
            name           : 'address',
            foreignKey     : 'address_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.Address',
            getterName     : 'getAddress',
            setterName     : 'setAddress'
        },
        {
            associationName: 'type',
            associationKey : 'type',
            instanceName   : 'type',
            name           : 'type',
            foreignKey     : 'type_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.ClientType',
            getterName     : 'getType',
            setterName     : 'setType'
        },
        {
            associationName: 'details',
            associationKey : 'details',
            instanceName   : 'details',
            name           : 'details',
            foreignKey     : 'details_id',
            type           : 'hasOne',
            model          : 'WMS.model.entity.ClientDetails',
            getterName     : 'getDetails',
            setterName     : 'setDetails'
        }
    ],
    proxy       : {
        type  : 'wms',
        url   : 'wms/agent/client',
        writer: {
            type          : 'json',
            root          : 'data',
            allowSingle   : false,
            writeAllFields: false,
            /**
             * Method is overridden due to need to
             * provide more thick data package. Instead
             * of POSTING client, address, clientDetails in
             * separate requests.
             * @param record
             * @return {*|String}
             */
            getRecordData : function (record) {
                var toBeSent = record.getData();
                toBeSent['address'] = record['address'];
                toBeSent['details'] = record['details'];
                toBeSent['type'] = record['type'];

                delete toBeSent['address_id'];
                delete toBeSent['details_id'];
                delete toBeSent['type_id'];

                return toBeSent;
            }
        }
    },
    statics     : {
        extract: function (raw) {
            var fields = ['address_id', 'type_id', 'details_id', 'description', 'name','company'],
                client = {};
            Ext.each(fields, function (chunk) {
                client[chunk] = raw[chunk];
            });
            return client;
        }
    }
});