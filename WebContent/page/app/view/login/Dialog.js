/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 24.10.12
 * Time   : 11:45
 */

/**
 * @class WMS.view.login.Dialog
 * @extend WMS.view.abstract.BaseDialog
 * @description This class embeds WMS.view.login.Form
 * in instance of dialog window. As WMS.view.abstract.BaseDialog
 * itself extends well known Ext.window.Window.
 */
Ext.define('WMS.view.login.Dialog', {
    extend: 'WMS.view.abstract.BaseDialog',
    uses  : [
        'WMS.view.abstract.BaseDialog',
        'WMS.view.login.Form'
    ],
    alias : 'widget.logindialog',
    title : 'Witaj w programie MajekWMS',
    layout: {
        type: 'fit'
    },
    items : [
        {
            xtype: 'loginform',
            url  : 'wms/auth'
        }
    ]
});