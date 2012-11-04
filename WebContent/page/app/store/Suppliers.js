/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:02
 */

Ext.define('WMS.store.Suppliers', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Supplier',

    autoLoad: true,
    autoSync: true
});