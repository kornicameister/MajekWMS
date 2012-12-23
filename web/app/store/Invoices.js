/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.12.12
 * Time     : 12:37
 */

/**
 * @class WMS.store.Invoices
 * @author kornicameister
 * @description This class is here to represent the storage
 * facility to hold invoices.
 */
Ext.define('WMS.store.Invoices', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.InvoiceProduct',
    autoLoad: true,
    autoSync: false
});