/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 03.10.12
 * Time   : 13:03
 */

Ext.define('WMS.store.Units',{
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Unit',

    autoLoad: true,
    autoSync: true
});