/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 14:45
 */

Ext.define('WMS.view.manager.recipient.Manager', {
    extend : 'WMS.panel.ClientManager',
    alias  : 'widget.clientmanager',
    title  : 'Menadżer - odbiorcy',
    iconCls: 'view-toolbar-clientsButton',
    uses   : [
        'WMS.view.manager.recipient.Details',
        'WMS.view.manager.recipient.Invoices'
    ],
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
                    xtype : 'clientdetails',
                    itemId: 'clientDetails',
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