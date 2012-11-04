/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 14:45
 */

Ext.define('WMS.view.manager.supplier.Manager', {
    extend : 'WMS.panel.ClientManager',
    alias  : 'widget.clientmanager',
    title  : 'Menadżer - odbiorcy',
    iconCls: 'view-toolbar-clientsButton',
    uses   : [
        'WMS.view.manager.supplier.Details',
        'WMS.view.manager.supplier.Invoices'
    ],
    items  : [
        {
            xtype  : 'clientgrid',
            itemId : 'supplierList',
            title  : 'Dostawcy',
            iconCls: 'view-toolbar-clientsButton',
            store  : 'Suppliers'
        },
        {
            xtype : 'managerdetailsholder',
            itemId: 'supplierDetailsHolder',
            items : [
                {
                    xtype : 'clientdetails',
                    itemId: 'clientDetails',
                    flex  : 1
                },
                {
                    xtype : 'supplierinvoices',
                    itemId: 'suppliesList',
                    flex  : 4
                }
            ]
        }
    ]
});