/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:13
 */

Ext.define('WMS.view.wizard.company.Dialog', {
    extend            : 'WMS.view.abstract.BaseDialog',
    requires          : [
        'WMS.view.abstract.BaseDialog',
        'WMS.view.wizard.company.CompanyForm',
        'WMS.view.wizard.company.WarehouseForm',
        'WMS.view.wizard.company.Summary'
    ],
    alternateClassName: 'WMS.wizard.Company',
    alias             : 'widget.companywizard',
    title             : 'Witaj w kreatorze pierwszego uruchomienia',
    autoShow          : true,
    autoRender        : true,
    width             : 700,
    minHeight         : 200,
    autoScroll        : true,
    layout            : {
        type: 'card'
    },
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
            xtype   : 'companysummary',
            itemId  : 'summaryStep',
            title   : 'Podsumowanie',
            flex    : 1,
            layout  : 'ux.center',
            defaults: {
                minHeight: 40,
                maxHeight: 50
            },
            bbar    : [
                {
                    itemId: 'saveCompany',
                    text  : 'Zapisz',
                    width : 'max',
                    height: 50
                }
            ]
        }
    ],
    bbar              : [
        {
            itemId  : 'prev',
            text    : 'Wstecz',
            height  : 30,
            width   : 120,
            disabled: true
        },
        '->',
        {
            itemId: 'next',
            text  : 'Dalej',
            height: 30,
            width : 120
        }
    ]
});
