/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 25.11.12
 * Time     : 01:10
 */

/**
 * @class WMS.store.InvoiceTypes
 * @description This class represent store unit configured
 * to operate directly read InvoiceTypes
 */
Ext.define('WMS.store.InvoiceTypes', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.InvoiceType',
    autoLoad: true,
    autoSync: false
});
