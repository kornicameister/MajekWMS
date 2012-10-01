/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : Warehouse
 * Package: view.wizard
 * Created: 28-09-2012
 */

Ext.define('WMS.view.wizard.Warehouse', {
    extend  : 'Ext.window.Window',
    alias   : 'widget.wizardwarehouse',
    itemId  : 'newWarehouseWizard',
    title   : 'New warehouse wizard',
    width   : 440,
    defaults: {
        border: false,
        frame : false
    },
    layout  : 'fit',

    items: {
        xtype : 'form',
        itemId: 'warehouseWizardForm',
        url   : 'wms/warehouse/save',

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
                        emptyText : 'Provide warehouse name'
                    },
                    {
                        xtype       : 'combobox',
                        fieldLabel  : 'Default type',
                        name        : 'warehouseDefaultType',
                        store       : Ext.create('Ext.data.JsonStore', {
                            autoDestroy: true,
                            model      : 'WMS.model.entity.UnitTypeSimple',
                            data       : Ext.StoreManager.lookup('Initial').getUnitTypes()
                        }),
                        valueField  : 'id',
                        displayField: 'name',
                        typeAhead   : true,
                        queryMode   : 'local',
                        emptyText   : 'Description of the warehouse'
                    },
                    {
                        xtype     : 'textarea',
                        fieldLabel: 'Description',
                        name      : 'description',
                        allowBlank: false,
                        emptyText : 'Select a default type for underlying units...'
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
                    step             : 20,
                    keyNavEnabled    : true,
                    mouseWheelEnabled: true
                }
            }
        ],

        buttons: [
            {
                text   : 'Reset',
                handler: function () {
                    this.up('form').getForm().reset();
                }
            },
            {
                id      : 'warehouseSubmitButton',
                itemId  : 'warehouseSubmit', // make it valid, instead of id property
                text    : 'Submit',
                formBind: true, //only enabled once the form is valid
                disabled: true
            }
        ]
    }

});