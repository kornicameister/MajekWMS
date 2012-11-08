/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 11:48
 */

Ext.define('WMS.controller.manager.Recipients', {
    extend                    : 'Ext.app.Controller',
    requires                  : [
        'WMS.view.wizard.client.Dialog'
    ],
    refs                      : [
        { ref: 'managerUI', selector: 'recipientmanager' },
        { ref: 'recipientList', selector: 'recipientmanager clientgrid'},
        { ref: 'detailsView', selector: 'recipientmanager recipientdetails'}
    ],
    views                     : [
        'WMS.view.manager.recipient.Manager'
    ],
    stores                    : [
        'Cities',
        'ClientTypes',
        'Recipients'
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
            'recipientmanager clientgrid'                          : {
                'itemdblclick': me.onDetailsClientClickGrid
            }
        });
    },
    onDetailsClientRequest    : function (client) {
        var me = this,
            detailsView = me.getDetailsView(),
            data = client.getData(true);

        if (Ext.isDefined(client) && Ext.isDefined(detailsView)) {
            detailsView.update(data);
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
        var me = this,
            store = me.getRecipientsStore(),
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