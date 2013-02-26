/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 11:22
 */

Ext.define('WMS.view.manager.abstract.InvoiceList', {
    extend : 'WMS.view.manager.abstract.Grid',
    uses   : [
        'WMS.view.manager.abstract.Grid',
        'WMS.model.entity.Invoice'
    ],
    store  : Ext.create('Ext.data.Store', {
        model   : 'WMS.model.entity.Invoice',
        autoLoad: false
    }),
    columns: [
        {
            text     : 'Numer',
            dataIndex: 'invoiceNumber',
            width    : 150
        },
        {
            text     : 'Odbiorca',
            dataIndex: 'client_id',
            width    : 150
        },
        {
            text     : 'Data wystawienia',
            dataIndex: 'createdDate'
        },
        {
            text     : 'Czas realizacji',
            dataIndex: 'timeToRealize'
        },
        {
            text     : 'Data realizacji',
            dataIndex: 'dueDate'
        },
        {
            text     : 'Opis',
            dataIndex: 'description'
        }
    ]
});