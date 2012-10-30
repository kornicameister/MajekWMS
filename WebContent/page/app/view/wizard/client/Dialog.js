/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 12:34
 */

Ext.define('WMS.view.wizard.client.Dialog', {
    extend: 'WMS.view.abstract.BaseDialog',
    alias : 'widget.wizardclient',
    title : 'Dodawanie nowego klienta',
    width : 400,

    requires: [
        'WMS.view.wizard.client.Form'
    ],

    items: {
        xtype: 'clientform'
    }
});