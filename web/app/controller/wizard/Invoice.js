/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.12.12
 * Time     : 03:17
 */

Ext.define('WMS.controller.wizard.Invoice', {
    extend           : 'Ext.app.Controller',
    requires         : [
        'Ext.ux.WMSColumnRenderers',
        'WMS.view.wizard.invoice.Invoice'
    ],
    views            : [
        'wizard.invoice.Invoice'
    ],
    stores           : [
        'InvoiceTypes',
        'Invoices'  // <-- this store entity is InvoiceProduct !!! -->
    ],
    statics          : {
        MODE: {
            SUPPLY : 'Suppliers',
            RECEIPT: 'Recipients'
        }
    },
    config           : {
        /**
         * This indicates current working mode,
         * it can be either Supplier or Recipient like.
         *
         * It is an object that references requires stores.
         * Choice highly depends on provided mode.
         */
        workingMode    : {},
        /**
         * @author kornicameister
         * @type Ext.data.Store
         * @since 0.1
         * @description This is the temporary store to be
         * used for holding products object that will be associated
         * with the invoice
         */
        productsStorage: Ext.create('Ext.data.Store', {
            model      : 'WMS.model.entity.Product',
            autoLoad   : false,
            autoSync   : false,
            autoDestroy: true,
            proxy      : {
                type: 'localstorage',
                id  : 'invoiceProductsTemporaryStorage'
            }
        }),
        /**
         * @type Object
         * @author kornicameister
         * @since 0.1
         * @description This variable is at the beginning the object literal
         * that hold information about the invoice being currently
         * created.
         */
        invoice        : {}
    },
    init             : function () {
        console.init('WMS.controller.wizard.Invoice initializing...');
    },
    // OPEN POINTS
    openAsReceipt    : function () {
        var me = this;
        me.openInvoiceWizard(WMS.controller.wizard.Invoice.MODE.RECEIPT);
    },
    openAsSupply     : function () {
        var me = this;
        me.openInvoiceWizard(WMS.controller.wizard.Invoice.MODE.SUPPLY);
    },
    openInvoiceWizard: function (mode) {
        var me = this,
            wizard = me.getView('wizard.invoice.Invoice'),
            title = '',
            storeLooker = Ext.StoreManager.lookup,
            wMode = {
                clientStore: storeLooker(mode)
            };

        // setting meta-data for invoice processing
        {
            switch (mode) {
                case  WMS.controller.wizard.Invoice.MODE.RECEIPT:
                    title = 'Nowe wydanie magazynowe';
                    break;
                case WMS.controller.wizard.Invoice.MODE.SUPPLY:
                    title = 'Nowe przyjęcie magazynowe';
                    break;
            }
            me.setWorkingMode(wMode);
        }
        // setting meta-data for invoice processing

        try {
            wizard.create({
                title: title
            }).show();
        } catch (e) {
            console.error(e);
        }
    }
    // OPEN POINTS
});