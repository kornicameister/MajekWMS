/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.12.12
 * Time     : 03:17
 */

Ext.define('WMS.controller.wizard.Invoice', {
    extend                   : 'Ext.app.Controller',
    uses                     : [
        'Ext.ux.WMSColumnRenderers',
        'WMS.view.wizard.invoice.Invoice',
        'WMS.model.entity.InvoiceProduct'
    ],
    views                    : [
        'wizard.invoice.Invoice'
    ],
    stores                   : [
        'InvoiceTypes',
        'Invoices'              // <-- this store entity is InvoiceProduct !!! -->
    ],
    refs                     : [
        { ref: 'invoiceTypesComboBox', selector: '#invoiceWindow invoiceform combo[itemId=invoiceTypeCB]'},
        { ref: 'clientComboBox', selector: '#invoiceWindow invoiceform combo[itemId=clientsCB]' },
        { ref: 'invoiceNumberTextField', selector: '#invoiceWindow invoiceform textfield[itemId=invoiceNumber]' },
        { ref: 'productsGrid', selector: '#invoiceWindow invoiceform egrid[itemId=invoiceProductsGrid]' },
        { ref: 'startDateField', selector: '#invoiceWindow invoiceform datefield[name=createdDate]'},
        { ref: 'endDateField', selector: '#invoiceWindow invoiceform datefield[name=dueDate]'},
        { ref: 'form', selector: '#invoiceWindow invoiceform'},
        { ref: 'wizard', selector: '#invoiceWindow'}
    ],
    statics                  : {
        MODE: {
            SUPPLY : 'supply',
            RECEIPT: 'receipt',
            RETURN : 'return'
        }
    },
    config                   : {
        /**
         * This indicates current working mode,
         * it can be either Supplier or Recipient like.
         *
         * It is an object that references requires stores.
         * Choice highly depends on provided mode.
         */
        workingMode           : {
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
        invoiceProductsStorage: undefined,
        /**
         * @author kornicameister
         * @type Ext.data.Store
         * @since 0.1.1
         * @description This store is used to provide the user
         * ability to select product he would like to add to
         * his invoice.
         */
        productsStorage       : undefined
    },
    init                     : function () {
        console.init('WMS.controller.wizard.Invoice initializing...');
        var me = this;

        me.control({
            '#invoiceWindow'                               : {
                'afterrender': me.onAfterRenderAction
            },
            '#productInvoiceCB'                            : {
                /**
                 * This it hack, At the moment of writing this part
                 * of the code, I was unable to bind the store to
                 * appropriate combo box (here -> productInvoiceCB)
                 * which is a part of the editor in the first column
                 * of the productsGrid
                 *
                 * @param combo reference to the target combo
                 */
                'afterrender': function (combo) {
                    combo.bindStore(me.getProductsStorage());
                }
            },
            'invoiceform egrid button[itemId=add]'         : {
                'click': me.onProductAdd
            },
            'invoiceform egrid button[itemId=delete]'      : {
                'click': me.onProductDelete
            },
            'invoiceform egrid[itemId=invoiceProductsGrid]': {
                'selectionchange': me.onProductSelectionChanged
            },
            'invoiceform button[itemId=submit]'            : {
                'click': me.onInvoiceSubmitClick
            },
            'invoiceform button[itemId=reset]'             : {
                'click': me.onInvoiceReset
            },
            'invoiceform button[itemId=cancel]'            : {
                'click': me.onInvoiceCancel
            }
        }, me);

    },
    /**
     * @description handler for click event
     * coming from submit button of the invoice
     * wizard
     */
    onInvoiceSubmitClick     : function (button) {
        var me = this,
            form = button.up('form').getForm(),
            values = form.getValues(),
            isValid = form.isValid(),
            type = (function () {
                var store = me.getInvoiceTypesStore(),
                    mode = me.getWorkingMode().getMode(),
                    typeIndex = store.find('name', mode);

                return store.getAt(typeIndex);
            }());

        if (!isValid) {
            Ext.MessageBox.warn(
                'Błąd',
                'Niepoprawnie wypełniony formularz'
            );
        }

        /*
         * Creation process
         * 1) create invoice
         * 2) send invoice to the server, get the respond
         * 3) associate that invoice with each product
         *    by creating InvoiceProductPK object
         * 4) add all InvoiceProduct objects into Invoices store
         */

        var invoice = Ext.create('WMS.model.entity.Invoice', values);
        invoice.setType(type);

        // step [1]
        invoice.save({
            callback: function (iRecord) {
                if (Ext.isArray(iRecord)) {
                    iRecord = iRecord[0];
                }

                var invoiceProductsStore = me.getInvoiceProductsStorage(),
                    invoices = me.getInvoicesStore();

                // step [2]
                invoiceProductsStore.each(function (rec) {
                    // step 3
                    rec['invoiceProduct'] = Ext.create('WMS.model.entity.InvoiceProductPK', {
                        product_id: rec.get('product_id'),
                        invoice_id: iRecord.getId()
                    });
                    rec['phantom'] = true;
                    invoices.add(rec);
                });

                // step [4]
                invoices.sync({
                    callback: function () {
                        me.closeWizard();
                    },
                    success : function () {
                        Ext.MessageBox.alert(
                            'Faktura utworzona',
                            'Nowa faktura została właśnie dodana'
                        )
                    },
                    failure : function () {
                        Ext.MessageBox.warn(
                            'Błąd',
                            'Nie udało się utworzyć nowej faktury'
                        );
                    }
                })
            }
        });
    },
    /**
     * @description handler for click event
     * coming from cancel button of this wizard
     */
    onInvoiceCancel          : function () {
        var me = this;
        me.closeWizard();
    },
    /**
     * @description handler for click event
     * coming from reset action...must reset
     * form and purge all data from temporary
     * store holding information about invoiceProducts
     *
     */
    onInvoiceReset           : function () {
        var me = this,
            form = me.getForm(),
            store = me.getInvoiceProductsStorage();

        form.reset();
        store.removeAll();
    },
    // product list handlers
    onProductAdd             : function () {
        console.log('wizard.Invoice :: Adding new product to existing invoice');

        var me = this,
            store = me.getInvoiceProductsStorage(),
            grid = me.getProductsGrid(),
            record = store.add(Ext.create('WMS.model.entity.InvoiceProduct'));

        if (!Ext.isDefined(record)) {
            console.error('wizard.Invoice :: Failed to add new product to invoice');
        }

        grid.getPlugin('invoiceProductRowEditor').startEdit(store.getTotalCount(), store.getTotalCount());
        Ext.getCmp('statusBar').setStatus({
            text : 'Właśnie dodałeś nowy produkt do faktury...',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },
    onProductDelete          : function () {
        console.log('wizard.Invoice :: Deleting selected products from existing invoice');

        var me = this,
            store = me.getInvoiceProductsStorage(),
            grid = me.getProductsGrid(),
            selection = grid.getView().getSelectionModel().getSelection();

        if (selection) {
            store.remove(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Usunąłeś {0} {1}',
                    selection.length,
                    selection.length > 0 ? 'produktów' : 'produkt'),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    },
    onProductSelectionChanged: function (selModel, selections) {
        var me = this,
            grid = me.getProductsGrid();
        grid.down('#delete').setDisabled(selections.length === 0);
    },
    // product list handlers
    onAfterRenderAction      : function () {
        var me = this,
            clientsCB = me.getClientComboBox(),
            iNumberTF = me.getInvoiceNumberTextField(),
            wMode = me.getWorkingMode(),
            productsGrid = me.getProductsGrid(),
            sDateDF = me.getStartDateField(),
            eDateDF = me.getEndDateField();

        // initializing products storage
        me.setInvoiceProductsStorage(Ext.create('Ext.data.Store', {
            model      : 'WMS.model.entity.InvoiceProduct',
            autoSync   : true,
            autoDestroy: true,
            proxy      : {
                type: 'localstorage',
                id  : 'invoiceProductsTemporaryStorage'
            },
            listeners  : {
                'update': function (store, record) {
                    var price = record.get('price'),
                        tax = record.get('tax'),
                        quantity = record.get('quantity'),
                        summaryPrice = price + (price * (tax / 100)),
                        product_id = record.get('product_id');

                    if ((price === 0 && tax === 22) && product_id !== 0) {
                        // first time triggered, setting price price
                        // tax from selected product
                        var product = me.getProductsStorage().getById(product_id);

                        record.set('price', product.get('price'));
                        record.set('tax', product.get('tax'));
                    }

                    summaryPrice *= quantity;
                    record.set('summaryPrice', summaryPrice);
                }
            }
        }));
        me.setProductsStorage(Ext.create('Ext.data.Store', {
            model      : 'WMS.model.entity.Product',
            autoLoad   : true,
            autoSync   : true,
            autoDestroy: true
        }));
        // initializing products storage

        // generating invoice number
        iNumberTF.setValue((function () {
            var dt = new Date();
            dt = 'F' + Ext.Date.format(dt, 'n/j/Y') + '/' + Ext.Date.format(dt, 'g_i[s]');
            return dt;
        }()));
        // generating invoice number

        wMode.getStore().reload({
            callback: function () {
                clientsCB.bindStore(wMode.getStore());
                productsGrid.reconfigure(me.getInvoiceProductsStorage());

                // settings date
                {
                    sDateDF.setValue(new Date());
                    /**
                     * This listeners updates field value
                     * in the eDateDF so it would match
                     * minimum difference between
                     * the start day and end day of the invoice.
                     */
                    me.mon(sDateDF, 'select', function (combo, nVal) {
                        eDateDF.setValue(Ext.Date.add(nVal, Ext.Date.DAY, 14)); //fourteen days to pay
                        eDateDF['minValue'] = Ext.Date.add(nVal, Ext.Date.DAY, 14);
                    });
                    eDateDF['minValue'] = Ext.Date.add(new Date(), Ext.Date.DAY, 14);
                    eDateDF.setValue(Ext.Date.add(new Date(), Ext.Date.DAY, 14)); //fourteen days to pay
                }
                // settings date

                // setting columns renderers
                {
                    productsGrid.columns[1]['renderer'] = function (product_id) {
                        if (!Ext.isDefined(product_id) || product_id === 0) {
                            return '';
                        } else if (Ext.isString(product_id)) {
                            product_id = parseInt(product_id);
                        }
                        return me.getProductsStorage().getById(product_id).get('name');
                    };
                    productsGrid.columns[5]['renderer'] = function (val) {
                        return val + ' zł';
                    };
                }
                // setting columns renderers
            }
        });
    },
    // OPEN POINTS
    openAsReceipt            : function () {
        var me = this;
        me.openInvoiceWizard(WMS.controller.wizard.Invoice.MODE.RECEIPT);
    },
    openAsSupply             : function () {
        var me = this;
        me.openInvoiceWizard(WMS.controller.wizard.Invoice.MODE.SUPPLY);
    },
    openInvoiceWizard        : function (mode) {

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
    },
    // OPEN POINTS,
    closeWizard              : function () {
        var me = this,
            store = me.getInvoiceProductsStorage(),
            wizard = me.getWizard();

        store.removeAll(true);
        wizard.close();
    }
});