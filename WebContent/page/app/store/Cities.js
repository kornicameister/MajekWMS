/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 17:25
 */

Ext.define('WMS.store.Cities', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.City',

    autoLoad: true,
    autoSync: true
});