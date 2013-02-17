/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.11.12
 * Time     : 03:01
 */

/**
 * @class WMS.view.wizard.invoice.Invoice
 * @author kornicameister
 * @version 0.1
 * @description This class wraps form provided for
 * creating new invoice.
 */
Ext.define('WMS.view.wizard.invoice.Invoice', {
    extend            : 'WMS.view.abstract.BaseDialog',
    requires          : [
        'WMS.view.abstract.BaseDialog',
        'WMS.view.wizard.invoice.InvoiceForm'
    ],
    alternateClassName: 'WMS.wizard.Invoice',
    title             : 'Nowa faktura',
    id                : 'invoiceWindow',
    items             : {
        xtype: 'invoiceform'
    },
    resizable         : false,
    plain             : true,
    border            : false,
    headerPosition    : 'left'
});
