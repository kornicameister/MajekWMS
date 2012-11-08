/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:47
 */

Ext.define('WMS.store.Companies', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.Company',
    autoLoad: false,
    autoSync: true
});