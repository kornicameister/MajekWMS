/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 24.10.12
 * Time   : 11:45
 */

Ext.define('WMS.view.login.LoginPanel', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.loginpanel',
    title : 'Welcome',

    layout: {
        type: 'fit'
    },

    items: [
        {
            xtype: 'loginform',
            title: 'Login credentials',
            url  : 'wms/auth'
        },
        {
            xtype: 'panel',
            title: 'Warehouses',
            items: {
                xtype : 'combo',
                itemId: 'warehouseSelector'
            }
        }
    ]
});