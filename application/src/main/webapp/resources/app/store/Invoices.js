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
    extend       : 'Ext.data.Store',
    model        : 'WMS.model.entity.InvoiceProduct',
    autoLoad     : true,
    autoSync     : false,
    findInvoices : function (record_id, property) {
        var me = this,
            recId = (Ext.isDefined(record_id.isModel) ? record_id.getId() : record_id),
            data = [];

        switch (property) {
            case 'client':
                me.each(function (rec) {
                    if (rec.getInvoiceProduct().getInvoice().getClient().getId() === recId) {
                        data.push(rec.getInvoiceProduct().getInvoice());
                    }
                }, me);
                break;
            case 'invoice':
                me.each(function (rec) {
                    if (rec.getInvoiceProduct().getInvoice().getId() === recId) {
                        data.push(rec);
                    }
                }, me);
                break;
        }

        return data;
    },
    findByProduct: function (p) {
        var me = this,
            product = (Ext.isDefined(p.isModel) ? p.getId() : p),
            product_found = undefined;

        me.each(function (rec) {
            if ((product_found = rec.getInvoiceProduct().getProduct()).getId() === product) {
                return false;
            }
        }, me);

        return product_found;
    }
});