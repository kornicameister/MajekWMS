/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:12
 */

Ext.define('WMS.view.wizard.company.CompanyForm', {
    extend            : 'Ext.form.Panel',
    requires          : [
        'Ext.ux.layout.Center'
    ],
    alternateClassName: 'WMS.form.Company',
    alias             : 'widget.companyform',
    bodyPadding       : 5,
    waitMsgTarget     : true,
    defaultType       : 'fieldset',
    layout            : 'ux.center',
    defaults          : {
        minHeight: 40,
        maxHeight: 50
    },
    items             : [
        {
            xtype     : 'textfield',
            fieldLabel: 'Skrót',
            name      : 'name',
            allowBlank: false,
            emptyText : 'Podaj skróconą nazwę firmy',
            maxLength : 45
        },
        {
            xtype     : 'textfield',
            fieldLabel: 'Nazwa',
            name      : 'longName',
            allowBlank: false,
            maxLength : 45
        }
    ]
});
