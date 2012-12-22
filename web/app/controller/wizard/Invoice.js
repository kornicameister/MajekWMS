/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.12.12
 * Time     : 03:17
 */

Ext.define('WMS.controller.wizard.Invoice', {
    extend  : 'Ext.app.Controller',
    requires: [
        'Ext.ux.WMSColumnRenderers',
        'WMS.view.wizard.invoice.Invoice'
    ],
    views   : [
        'wizard.invoice.Invoice'
    ],
    stores  : [
        'InvoiceTypes'
    ],
    init    : function () {
        console.init('WMS.controller.wizard.Invoice initializing...');
    }
});