/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 14:45
 */

Ext.define('WMS.view.manager.supplier.Manager', {
    extend : 'WMS.view.manager.abstract.ClientManager',
    uses   : [
        'WMS.view.manager.abstract.ClientManager',
        'WMS.view.manager.supplier.Details',
        'WMS.view.manager.supplier.Invoices'
    ],
    alias  : 'widget.suppliermanager',
    title  : 'Menadżer - dostawcy',
    iconCls: 'view-toolbar-clientsButton',
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
                    xtype : 'supplierdetails',
                    itemId: 'supplierDetails',
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