/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 17:12
 */

Ext.define('WMS.model.entity.City', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'id', type: 'int'},
        { name: 'name', type: 'string'}
    ],

    sorters: [
        {
            property:'name'
        }
    ],

    proxy: {
        type: 'wms',
        url : 'wms/agent/city'
    }
});