/**
 * Project: Majek WMS User : kornicameister Date : 24.09.12 Time : 22:38 File :
 * WMSPageConfiguration Package: page Created: 24-09-2012
 */

Ext.define('WMS.model.WMSConfiguration', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name        : 'displayName',
            type        : 'string',
            defaultValue: 'MajekWMS'
        },
        {
            name        : 'warehouse',
            type        : 'object',
            defaultValue: {}
        }
    ],

    proxy: {
        type       : 'rest',
        api        : {
            read: 'wms/start'
        },
        reader     : {
            type           : 'json',
            root           : 'users',
            successProperty: 'success'
        },
        // params
        startParam : undefined,
        limitParam : undefined,
        pageParam  : undefined,
        extraParams: {
            requires: {
                property: ['warehouse', 'units', 'history']
            }
        }
    }
}, function () {
    console.log('WMS.model.WMSConfiguration successfully defined');
});
