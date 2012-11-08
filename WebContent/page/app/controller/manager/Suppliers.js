/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:31
 */

Ext.define('WMS.controller.manager.Suppliers', {
    extend                    : 'Ext.app.Controller',
    requires                  : [
        'WMS.view.wizard.client.Dialog'
    ],
    refs                      : [
        { ref: 'managerUI', selector: 'suppliermanager' },
        { ref: 'supplierList', selector: 'suppliermanager clientgrid'},
        { ref: 'detailsView', selector: 'suppliermanager supplierdetails'}
    ],
    views                     : [
        'WMS.view.manager.supplier.Manager'
    ],
    stores                    : [
        'Cities',
        'ClientTypes',
        'Suppliers'
    ],
    init                      : function () {
        console.init('WMS.controller.manager.Suppliers is initializing...');
        var me = this;
        me.control({
            'suppliermanager toolbar button[itemId=newClient]'    : {
                'click': me.onNewClientAddClick
            },
            'suppliermanager toolbar button[itemId=removeClient]' : {
                'click': me.onRemoveClientClick
            },
            'suppliermanager toolbar button[itemId=editClient]'   : {
                'click': me.onEditClientClick
            },
            'suppliermanager toolbar button[itemId=detailsClient]': {
                'click': me.onDetailsClientClickButton
            },
            'suppliermanager toolbar button[itemId=releaseClient]': {
                'click': me.onReleaseClientClick
            },
            'suppliermanager clientgrid'                          : {
                'itemdblclick': me.onDetailsClientClickGrid
            }
        });
        me.callParent(arguments);
    },
    onDetailsClientRequest    : function (client) {
        var me = this,
            detailsView = me.getDetailsView(),
            data = client.getData(true);

        if (Ext.isDefined(client) && Ext.isDefined(detailsView)) {
            detailsView.update(data);
            me.popupDetailsView();

            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Wyświetlam szczegóły dostawcy {0}', client.get('name')),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            })
        }
    },
    onReleaseClientClick      : function () {
        var me = this,
            store = me.getSupplierList(),
            selection = me.getSelectedClients();
    },
    onNewClientAddClick       : function (button) {
        var me = this,
            clientWizard = me.getView('wizard.client.Dialog');
        clientWizard.create().show(button);
    },
    onEditClientClick         : function () {

    },
    onRemoveClientClick       : function () {
        var me = this,
            store = me.getSupplierList(),
            selection = me.getSelectedClients();
        if (selection) {
            store.remove(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Usunąłeś {0} {1}',
                    selection.length,
                    selection.length > 0 ? 'dostawców' : 'dostawcę'),
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
        return me.getSupplierList().getView().getSelectionModel().getSelection();
    },
    popupDetailsView          : function () {
        var me = this,
            managerUI = me.getManagerUI(),
            detailsView = managerUI['items'].get('supplierDetailsHolder');
        if (Ext.isDefined(detailsView)) {
            detailsView.expand();
        } else {
            console.log('manager.Recipients :: Failed to popup with statistics')
        }
    }, /**
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