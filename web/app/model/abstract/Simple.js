/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 01:40
 */

Ext.define('WMS.model.abstract.Simple', {
    extend : 'Ext.data.Model',
    fields : [
        { name: 'id', type: 'int', defaultValue: null},
        { name: 'name', type: 'string' }
    ],
    sorters: [
        { property: 'name' }
    ]
});
