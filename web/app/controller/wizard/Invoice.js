/**
 * Project  : MajekWMS
 * User     : kornicameister
 * Date     : 22.12.12
 * Time     : 03:17
 */

Ext.define('WMS.controller.wizard.Invoice', {
    extend                   : 'Ext.app.Controller',
    requires                 : [
        'Ext.ux.WMSColumnRenderers',
        'WMS.view.wizard.invoice.Invoice',
        'WMS.model.entity.Product',
        'WMS.model.entity.InvoiceProduct'
    ],
    views                    : [
        'wizard.invoice.Invoice'
    ],
    stores                   : [
        'InvoiceTypes',
        'Invoices'  // <-- this store entity is InvoiceProduct !!! -->
    ],
    refs                     : [
        { ref: 'invoiceTypesComboBox', selector: 'invoiceform combo[itemId=invoiceTypeCB]'},
        { ref: 'clientComboBox', selector: 'invoiceform combo[itemId=clientsCB]' },
        { ref: 'invoiceNumberTextField', selector: 'invoiceform textfield[itemId=invoiceNumber]' },
        { ref: 'productsGrid', selector: 'invoiceform egrid[itemId=invoiceProductsGrid]' },
        { ref: 'startDateField', selector: 'invoiceform datefield[name=createdDate]'},
        { ref: 'endDateField', selector: 'invoiceform datefield[name=dueDate]'}
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
            mode     : undefined,
            store    : undefined,
            plName   : undefined,
            getMode  : function () {
                return this.mode;
            },
            getStore : function () {
                return this.store;
            },
            getPLMode: function () {
                return this.plName;
            },
            setStore : function (store) {
                this.store = store;
            },
            setMode  : function (mode) {
                this.mode = mode;
            },
            setPLMode: function (name) {
                this.plName = name;
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
        productsStorage       : undefined,
        /**
         * @type Object
         * @author kornicameister
         * @since 0.1
         * @description This variable is at the beginning the object literal
         * that hold information about the invoice being currently
         * created.
         */
        invoice               : undefined
    },
    init                     : function () {
        console.init('WMS.controller.wizard.Invoice initializing...');
        var me = this;

        // initializing products storage
        me.setInvoiceProductsStorage(Ext.create('Ext.data.Store', {
            model      : 'WMS.model.entity.InvoiceProduct',
            autoLoad   : false,
            autoSync   : false,
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

        me.control({
            '#invoiceWindow'                               : {
                'afterrender': me.updateMetaData
            },
            '#productInvoiceCB'                            : {
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
                selectionchange: me.onProductSelectionChanged
            }
        }, me);

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
    updateMetaData           : function () {
        var me = this,
            invoiceTypeCB = me.getInvoiceTypesComboBox(),
            clientsCB = me.getClientComboBox(),
            iNumberTF = me.getInvoiceNumberTextField(),
            wMode = me.getWorkingMode(),
            productsGrid = me.getProductsGrid(),
            invoiceProductsStorage = me.getInvoiceProductsStorage(),
            productsStorage = me.getProductsStorage(),
            sDateDF = me.getStartDateField(),
            eDateDF = me.getEndDateField();

        // generating invoice number
        iNumberTF.setValue((function () {
            var dt = new Date();
            dt = 'F' + Ext.Date.format(dt, 'n/j/Y') + '/' + Ext.Date.format(dt, 'g_i[s]');
            return dt;
        }()));

        wMode.getStore().reload({
            callback: function () {
                invoiceTypeCB.setValue(wMode.getPLMode());
                clientsCB.bindStore(wMode.getStore());
                productsGrid.reconfigure(invoiceProductsStorage);

                // settings date
                sDateDF.setValue(new Date());
                eDateDF.setValue(Ext.Date.add(new Date(), Ext.Date.DAY, 14)); //fourteen days to pay

                // setting columns renderers
                productsGrid.columns[1]['renderer'] = function (product_id) {
                    if (!Ext.isDefined(product_id)) {
                        return '';
                    } else if (Ext.isString(product_id)) {
                        product_id = parseInt(product_id);
                    }
                    if (product_id === 0) {
                        return;
                    }
                    return productsStorage.getById(product_id).get('name');
                }
                productsGrid.columns[5]['renderer'] = function (val) {
                    return val + ' zł';
                }
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
            store = undefined,
            plName = undefined;

        // setting meta-data for invoice processing
        {
            switch (mode) {
                case  WMS.controller.wizard.Invoice.MODE.RECEIPT:
                    title = 'Nowe wydanie magazynowe';
                    store = Ext.StoreManager.lookup('Recipients');
                    plName = 'Wydanie';
                    break;
                case WMS.controller.wizard.Invoice.MODE.SUPPLY:
                    title = 'Nowe przyjęcie magazynowe';
                    store = Ext.StoreManager.lookup('Suppliers');
                    plName = 'Przyjęcie';
                    break;
            }
            wMode.setStore(store);
            wMode.setMode(mode);
            wMode.setPLMode(plName);
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