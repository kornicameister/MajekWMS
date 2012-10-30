/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 14:58
 */

Ext.define('WMS.model.entity.Address', {
    extend: 'Ext.data.Model',
    fields: [
        'street',
        'postcode',
        'city'
    ]
});