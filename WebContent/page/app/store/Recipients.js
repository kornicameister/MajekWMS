/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:24
 */

Ext.define('WMS.store.Recipients', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Recipient',

    autoLoad: true,
    autoSync: true
});