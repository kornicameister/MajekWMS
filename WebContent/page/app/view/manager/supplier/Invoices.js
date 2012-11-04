/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 02.11.12
 * Time   : 15:15
 */

Ext.define('WMS.view.manager.supplier.Invoices', {
    extend    : 'WMS.view.manager.abstract.InvoiceList',
    alias     : 'widget.supplierinvoices',
    title     : 'Dostawy',
    viewConfig: {
        emptyText: 'Brak dostaw od tego dostawcy'
    }
});