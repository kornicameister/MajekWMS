/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 15:03
 */

Ext.define('WMS.view.wizard.Unit', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.wizardunit',

    items: {
        xtype  : 'form',
        items  : [
            {
                xtype     : 'textfield',
                fieldLabel: 'Name',
                name      : 'name',
                allowBlank: false,
                emptyText : 'Provide unit name',
                maxLength : 45
            },
            {
                xtype     : 'combo',
                fieldLabel: 'Default type',
                name      : 'type',

                store       : Ext.create('WMS.store.UnitTypes', {
                    storeId    : 'UnitTypes',
                    autoDestroy: true
                }),
                valueField  : 'id',
                displayField: 'name',

                typeAhead     : true,
                forceSelection: true,
                allowBlank    : false,

                emptyText: 'Description of the warehouse'
            },
            {
                xtype     : 'textarea',
                fieldLabel: 'Description',
                name      : 'description',
                allowBlank: false,
                emptyText : 'Short unit description',
                maxLength : 250
            },
            {
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
        ],
        buttons: [
            {
                text   : 'Reset',
                handler: function () {
                    this.up('form').getForm().reset();
                }
            },
            {
                itemId  : 'submitUnit', // make it valid, instead of id property
                text    : 'Create',
                iconCls : 'view-wizard-submit',
                formBind: true, //only enabled once the form is valid
                disabled: true
            }
        ]
    }
});