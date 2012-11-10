/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 14:45
 */

Ext.define('WMS.view.manager.recipient.Manager', {
    extend : 'WMS.view.manager.abstract.ClientManager',
    uses   : [
        'WMS.view.manager.abstract.ClientManager',
        'WMS.view.manager.recipient.Details',
        'WMS.view.manager.recipient.Invoices'
    ],
    alias  : 'widget.recipientmanager',
    title  : 'Menadżer - odbiorcy',
    iconCls: 'view-toolbar-clientsButton',
    items  : [
        {
            xtype  : 'clientgrid',
            itemId : 'recipientList',
            title  : 'Odbiorcy',
            iconCls: 'view-toolbar-clientsButton',
            store  : 'Recipients'
        },
        {
            xtype : 'managerdetailsholder',
            itemId: 'recipientDetailsHolder',
            items : [
                {
                    xtype : 'recipientdetails',
                    itemId: 'recipientDetails',
                    flex  : 1
                },
                {
                    xtype : 'recipientinvoices',
                    itemId: 'receiptsList',
                    flex  : 4
                }
            ]
        }
    ]
});