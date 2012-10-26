/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 24.10.12
 * Time   : 11:45
 */

Ext.define('WMS.view.login.Dialog', {
    extend  : 'WMS.view.abstract.BaseDialog',
    alias   : 'widget.logindialog',
    title   : 'Welcome',
    autoShow: true,

    items  : [
        {
            xtype: 'loginform',
            title: 'Login credentials',
            url  : 'wms/auth'
        },
        {
            xtype  : 'panel',
            title  : 'Warehouses',
            enabled: false,
            items  : {
                xtype : 'combo',
                itemId: 'warehouseSelector'
            }
        }
    ],
    buttons: [
        {
            itemId: 'openWarehouseButton',
            text  : 'Open warehouse'
        }
    ]
});