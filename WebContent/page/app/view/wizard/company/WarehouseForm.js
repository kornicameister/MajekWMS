/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:18
 */

Ext.define('WMS.view.wizard.company.WarehouseForm', {
    extend            : 'Ext.form.Panel',
    alternateClassName: 'WMS.form.Warehouse',
    alias             : 'widget.warehouseform',
    bodyPadding       : 5,
    waitMsgTarget     : true,
    defaultType       : 'fieldset',
    defaults          : {
        layout: 'fit'
    },
    items             : [
        {
            itemId: 'basicInfoFieldSet',
            title : 'Basic information',
            items : [
                {
                    xtype     : 'textfield',
                    fieldLabel: 'Name',
                    name      : 'name',
                    allowBlank: false,
                    emptyText : 'Provide warehouse name',
                    maxLength : 20
                },
                {
                    xtype     : 'textarea',
                    fieldLabel: 'Description',
                    name      : 'description',
                    allowBlank: false,
                    emptyText : 'Select a default type for underlying units...',
                    maxLength : 666
                },
                {
                    xtype     : 'datefield',
                    fieldLabel: 'Created on',
                    name      : 'createdDate',
                    allowBlank: false,
                    value     : new Date()
                }
            ]
        },
        {
            itemId: 'numbersFieldSet',
            title : 'Some numbers',
            items : {
                xtype            : 'numberfield',
                fieldLabel       : 'Size',
                name             : 'size',
                allowBlank       : false,
                value            : 100,
                minValue         : 1,
                maxValue         : Number.MAX_VALUE,
                step             : 200,
                keyNavEnabled    : true,
                mouseWheelEnabled: true
            }
        }
    ]
});