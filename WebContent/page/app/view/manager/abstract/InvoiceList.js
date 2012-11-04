/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 11:22
 */

Ext.define('WMS.view.manager.abstract.InvoiceList', {
    extend            : 'WMS.grid.Panel',
    alternateClassName: 'WMS.grid.Invoices',
    alias             : 'widget.invoices',
    columns           : [
        {
            header   : 'ID',
            dataIndex: 'id',
            width    : 20,
            hidden   : true
        },
        {
            header   : 'Numer',
            dataIndex: 'refNumber',
            width    : 100
        },
        {
            header   : 'Data wystawienia',
            dataIndex: ' createdData'
        },
        {
            header   : 'Data realizacji',
            dataIndex: 'dueData'
        },
        {
            header   : 'Odbiorca',
            dataIndex: 'client_id',
            width    : 200
        },
        {
            header   : 'Opis',
            dataIndex: 'description'
        }
    ]
});