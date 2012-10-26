/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 24.10.12
 * Time   : 11:45
 */

/**
 * @class WMS.view.login.Dialog
 */
Ext.define('WMS.view.login.Dialog', {
    extend    : 'WMS.view.abstract.BaseDialog',
    alias     : 'widget.logindialog',
    title     : 'Welcome',
    autoShow  : true,
    autoRender: true,

    layout: {
        type : 'fit'
    },

    items  : [
        {
            xtype: 'loginform',
            url  : 'wms/auth'
        }
    ]
});