/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:13
 */

Ext.define('WMS.view.wizard.company.Dialog', {
    extend            : 'WMS.view.abstract.BaseDialog',
    alternateClassName: 'WMS.wizard.Company',
    alias             : 'widget.companywizard',
    title             : 'Witaj',
    autoShow          : true,
    autoRender        : true,
    layout            : {
        type: 'card'
    },
    uses              : [
        'WMS.view.wizard.company.CompanyForm',
        'WMS.view.wizard.company.WarehouseForm',
        'WMS.view.wizard.company.Summary'
    ],
    items             : [
        {
            xtype : 'panel',
            layout: 'fit',
            itemId: 'welcomeStep',
            html  : 'Witaj w kretorze konfiguracji programu MajekWMS, kliknij dalej, aby zdefiniować firmę'
        },
        {
            xtype : 'companyform',
            itemId: 'companyStep',
            url   : 'wms/agent/company'
        },
        {
            xtype : 'warehouseform',
            itemId: 'warehouseStep',
            url   : 'wms/agent/warehouse'
        },
        {
            xtype : 'panel',
            itemId: 'summaryStep',
            layout: {
                type: 'center'
            },
            items : {
                xtype: 'companysummary'
            },
            bbar  : [
                {
                    itemId: 'saveCompany',
                    text  : 'Zapisz',
                    width : 200,
                    height: 50
                }
            ]
        }
    ],
    bbar              : [
        {
            itemId  : 'prev',
            text    : 'Wstecz',
            disabled: true
        },
        '->',
        {
            itemId: 'next',
            text  : 'Dalej'
        }
    ]
});