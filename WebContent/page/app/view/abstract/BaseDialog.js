/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 09.10.12
 * Time  : 23:22
 */

Ext.define('WMS.view.abstract.BaseDialog', {
    extend: 'Ext.window.Window',

    defaults   : {
        border: false,
        frame : false
    },
    layout     : 'fit',
    closable   : false,
    bodyPadding: 5,
    constrain  : true
});