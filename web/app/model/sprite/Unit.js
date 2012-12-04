/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 09:44
 */

Ext.define('WMS.model.sprite.Unit', {
    extend: 'Ext.data.Model',
    fields: [
        'id', 'sprite', 'unit_id'
    ],
    proxy : {
        type: 'localstorage',
        id  : 'unitsprites'
    }
});