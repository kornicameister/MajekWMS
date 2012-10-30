/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 15:02
 */

Ext.define('WMS.model.entity.ClientDetails', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'nip', type: 'string'},
        {name: 'phone', type: 'string'},
        {name: 'fax', type: 'string'},
        {name: 'account', type: 'string'}
    ]
});