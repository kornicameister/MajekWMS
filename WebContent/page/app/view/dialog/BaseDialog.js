/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 09.10.12
 * Time  : 23:22
 */

Ext.define('WMS.view.dialog.BaseDialog', {
    extend: 'Ext.window.Window',
    width : 440,

    defaults   : {
        border: false,
        frame : false
    },
    layout     : 'fit',
    closable   : false,
    bodyPadding: 5,
    constrain  : true
});