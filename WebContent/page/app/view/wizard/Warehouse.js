/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : Warehouse
 * Package: view.wizard
 * Created: 28-09-2012
 */

Ext.define('WMS.view.dialog.Warehouse', {
    extend: 'Ext.window.Window',
    alias : 'widget.wizardwarehouse',
    title : 'New warehouse wizard',
    width : 440,

    items: {
        xtype: 'form',
        url  : 'wms/warehouse/save',

        bodyPadding  : 5,
        waitMsgTarget: true,

        defaultType: 'fieldset',
        defaults   : {
            layout: 'fit'
        },
        items      : [
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
                    name             : 'maximumSize',
                    allowBlank       : false,
                    value            : 100,
                    minValue         : 1,
                    maxValue         : Number.MAX_VALUE,
                    step             : 200,
                    keyNavEnabled    : true,
                    mouseWheelEnabled: true
                }
            }
        ],
        buttons    : [
            {
                text   : 'Reset',
                handler: function () {
                    this.up('form').getForm().reset();
                }
            },
            {
                itemId  : 'submitButton', // make it valid, instead of id property
                text    : 'Submit',
                iconCls : 'view-wizard-submit',
                formBind: true, //only enabled once the form is valid
                disabled: true
            }
        ]
    }
});