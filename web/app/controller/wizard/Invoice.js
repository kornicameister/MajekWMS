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
        'WMS.view.wizard.invoice.Invoice',
        'WMS.model.entity.Product'
    ],
    views            : [
        'wizard.invoice.Invoice'
    ],
    stores           : [
        'InvoiceTypes',
        'Invoices'  // <-- this store entity is InvoiceProduct !!! -->
    ],
    refs             : [
        { ref: 'invoiceTypesComboBox', selector: 'invoiceform combo[itemId=invoiceTypeCB]'},
        { ref: 'clientComboBox', selector: 'invoiceform combo[itemId=clientsCB]' },
        { ref: 'invoiceNumberTextField', selector: 'invoiceform textfield[itemId=invoiceNumber]' }
    ],
    statics          : {
        MODE: {
            SUPPLY : 'supply',
            RECEIPT: 'receipt',
            RETURN : 'return'
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
        workingMode    : {
            mode    : undefined,
            store   : undefined,
            getMode : function () {
                return this.mode;
            },
            getStore: function () {
                return this.store;
            },
            setStore: function (store) {
                this.store = store;
            },
            setMode : function (mode) {
                this.mode = mode;
            }
        },
        /**
         * @author kornicameister
         * @type Ext.data.Store
         * @since 0.1
         * @description This is the temporary store to be
         * used for holding products object that will be associated
         * with the invoice
         */
        productsStorage: undefined,
        /**
         * @type Object
         * @author kornicameister
         * @since 0.1
         * @description This variable is at the beginning the object literal
         * that hold information about the invoice being currently
         * created.
         */
        invoice        : undefined
    },
    init             : function () {
        console.init('WMS.controller.wizard.Invoice initializing...');
        var me = this;

        /**
         * Initializing products' storage
         */
        me.setProductsStorage(Ext.create('Ext.data.Store', {
            model      : 'WMS.model.entity.Product',
            autoLoad   : false,
            autoSync   : false,
            autoDestroy: true,
            proxy      : {
                type: 'localstorage',
                id  : 'invoiceProductsTemporaryStorage'
            }
        }));

        me.control({
            '#invoiceWindow': {
                'afterrender': me.updateMetaData
            }
        }, me);

    },
    updateMetaData   : function () {
        var me = this,
            invoiceTypeCB = me.getInvoiceTypesComboBox(),
            clientsCB = me.getClientComboBox(),
            iNumberTF = me.getInvoiceNumberTextField(),
            wMode = me.getWorkingMode();

        // generating invoice number
        iNumberTF.setValue((function () {
            var dt = new Date();
            dt = 'F' + Ext.Date.format(dt, 'n/j/Y') + '/' + Ext.Date.format(dt, 'g_i[s]');
            return dt;
        }()));

        wMode.getStore().reload({
            callback: function () {
                invoiceTypeCB.setValue(wMode.getMode());
                clientsCB.bindStore(wMode.getStore());
            }
        });
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

        if (Ext.getCmp('invoiceWindow')) {
            Ext.MessageBox.alert(
                'Błąd',
                'Już edytujesz jedną fakturę. ' +
                    'Aby móc utworzyć nową fakturę ' +
                    'musisz zakończyć tworzenie obecnej'
            );
            return;
        }

        var me = this,
            wizard = me.getView('wizard.invoice.Invoice'),
            title = '',
            wMode = me.getWorkingMode(),
            store = undefined;

        // setting meta-data for invoice processing
        {
            switch (mode) {
                case  WMS.controller.wizard.Invoice.MODE.RECEIPT:
                    title = 'Nowe wydanie magazynowe';
                    store = Ext.StoreManager.lookup('Recipients');
                    break;
                case WMS.controller.wizard.Invoice.MODE.SUPPLY:
                    title = 'Nowe przyjęcie magazynowe';
                    store = Ext.StoreManager.lookup('Suppliers');
                    break;
            }
            wMode.setStore(store);
            wMode.setMode(mode);
            me.setWorkingMode(wMode);
        }
        // setting meta-data for invoice processing

        try {
            wizard.create(
                {
                    title: title
                }
            ).show();
        } catch (e) {
            console.error(e);
        }
    }
    // OPEN POINTS
});