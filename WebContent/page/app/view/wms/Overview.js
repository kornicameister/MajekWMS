/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 04:36
 * File   : Warehouse
 * Package: view.wms
 * Created: 14-09-2012
 */

Ext.define('WMS.view.wms.Overview', {
    extend : 'Ext.panel.Panel',
    alias  : 'widget.wmsoverviews',
    iconCls: 'view-wms-overview',

    requires: [
        'WMS.view.wizard.Unit'
    ],

    layout: {
        type   : 'hbox',
        align  : 'stretch',
        pack   : 'center'
    },

    items: [
        {
            xtype   : 'panel',
            flex    : 1,
            layout  : {
                type   : 'vbox',
                align  : 'stretch',
                pack   : 'bottom'
            },
            defaults: {
                collapsible: true
            },
            items   : [
                {
                    xtype : 'panel',
                    itemId: 'warehouseDescription'
                },
                {
                    xtype    : 'wizardunit',
                    itemId   : 'unitWizardForm',
                    collapsed: true
                }
            ]
        },
        {
            xtype: 'splitter'
        },
        {
            xtype  : 'grid',
            itemId : 'unitsGrid',
            flex   : 3,
            store  : Ext.getStore('Units'),
            columns: [
                {header: 'Name', dataIndex: 'name'},
                {header: 'Size', dataIndex: 'size'},
                {header: 'Maximum size', dataIndex: 'maxSize'},
                {header: 'Description', dataIndex: 'description'}
            ]
        }
    ]
});