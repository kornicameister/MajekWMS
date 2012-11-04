/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 02.11.12
 * Time   : 15:15
 */

Ext.define('WMS.view.manager.recipient.Invoices', {
    extend    : 'WMS.grid.Invoices',
    alias     : 'widget.recipientinvoices',
    title     : 'Wydania',
    viewConfig: {
        emptyText: 'Brak wydań dla tego odbiorcy'
    }
});