/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 11:48
 */

Ext.define('WMS.controller.manager.Recipients', {
    extend                    : 'Ext.app.Controller',
    refs                      : [
        { ref: 'managerUI', selector: 'recipientmanager' },
        { ref: 'recipientList', selector: 'recipientmanager clientgrid'},
        { ref: 'detailsView', selector: 'recipientmanager recipientdetails'},
        { ref: 'invoicesView', selector: 'recipientmanager recipientinvoices'}
    ],
    stores                    : [
        'Cities',
        'ClientTypes',
        'Recipients',
        'Invoices'
    ],
    init                      : function () {
        console.init('WMS.controller.manager.Recipients is initializing...');
        var me = this;
        me.control({
            'recipientmanager toolbar button[itemId=newClient]'    : {
                'click': me.onNewClientAddClick
            },
            'recipientmanager toolbar button[itemId=removeClient]' : {
                'click': me.onRemoveClientClick
            },
            'recipientmanager toolbar button[itemId=editClient]'   : {
                'click': me.onEditClientClick
            },
            'recipientmanager toolbar button[itemId=detailsClient]': {
                'click': me.onDetailsClientClickButton
            },
            'recipientmanager toolbar button[itemId=releaseClient]': {
                'click': me.onReleaseClientClick
            },
            'recipientmanager toolbar button[itemId=newInvoice]'   : {
                'click': me.onNewInvoiceClick
            },
            'recipientmanager clientgrid'                          : {
                'itemdblclick': me.onDetailsClientClickGrid
            },
            'recipientmanager recipientinvoices'                   : {
                'render': me.onInvoiceListRendered
            }
        }, me);
    },
    onInvoiceListRendered     : function () {
        var me = this,
            grid = me.getInvoicesView(),
            clientRenderer = function (client_id) {
                if (!Ext.isDefined(client_id) || client_id === 0) {
                    return '';
                } else if (Ext.isString(client_id)) {
                    client_id = parseInt(client_id);
                }
                return me.getRecipientsStore().getById(client_id).get('name');
            },
            dateRenderer = function (date) {
                return Ext.Date.format(date, 'Y-n-j');
            },
            timeRenderer = function (days) {
                return Ext.String.format('{0} dni', days);
            };

        grid.columns[1]['renderer'] = clientRenderer;
        grid.columns[2]['renderer'] = dateRenderer;
        grid.columns[3]['renderer'] = timeRenderer;
        grid.columns[4]['renderer'] = dateRenderer;

    },
    onNewInvoiceClick         : function () {
        var me = this,
            invoiceWizardCtrl = me.getController('WMS.controller.wizard.Invoice');

        invoiceWizardCtrl.openAsReceipt();
    },
    onDetailsClientRequest    : function (client) {
        var me = this,
            detailsView = me.getDetailsView(),
            invoiceView = me.getInvoicesView(),
            data = client.getData(true);

        if (Ext.isDefined(client) && Ext.isDefined(detailsView)) {
            detailsView.update(data);
            invoiceView.reconfigure((function () {
                var client_id = client.getId(),
                    invoicesStore = me.getInvoicesStore(),
                    invoices = invoicesStore.findInvoices(client_id);

                return Ext.create('Ext.data.Store', {
                    model: 'WMS.model.entity.Invoice',
                    data : invoices
                });

            }()));
            me.popupDetailsView();

            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Wyświetlam szczegóły odbiorcy {0}', client.get('name')),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            })
        }
    },
    onReleaseClientClick      : function () {
    },
    onNewClientAddClick       : function () {
        var me = this,
            clientWizardCtrl = me.getController('WMS.controller.wizard.Client');

        if (Ext.isDefined(clientWizardCtrl)) {
            clientWizardCtrl.openAsRecipient();
        }
    },
    onEditClientClick         : function () {

    },
    onRemoveClientClick       : function () {
        var me = this,
            store = me.getRecipientsStore(),
            selection = me.getSelectedClients();
        if (selection) {
            store.remove(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Usunąłeś {0} {1}',
                    selection.length,
                    selection.length > 0 ? 'odbiorców' : 'odbiorcę'),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    },
    //-----------UTILS and WRAPPERS ----------------//
    /**
     * Utility method returning selected clients.
     * Selection is understood as rows being currently selected
     * in the clients list
     * @return {*}
     */
    getSelectedClients        : function () {
        var me = this;
        return me.getRecipientList().getView().getSelectionModel().getSelection();
    },
    popupDetailsView          : function () {
        var me = this,
            managerUI = me.getManagerUI(),
            detailsView = managerUI['items'].get('recipientDetailsHolder');
        if (Ext.isDefined(detailsView)) {
            detailsView.expand();
        } else {
            console.log('manager.Recipients :: Failed to popup with statistics')
        }
    },
    /**
     * Method used as wrapper for the right method which is
     * to process the request
     * @param grid
     * @param record
     */
    onDetailsClientClickGrid  : function (grid, record) {
        var me = this;
        me.onDetailsClientRequest(record);
    },
    /**
     * Wrapper for the method used to display details for
     * selected client. It is called as the reaction for clicking
     * button in bottom toolbar.
     */
    onDetailsClientClickButton: function () {
        var me = this,
            selection = me.getSelectedClients();
        me.onDetailsClientRequest(selection[0]);
    }
});
