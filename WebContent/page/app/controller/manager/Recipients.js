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
    views                     : [
        'WMS.view.manager.client.Manager'
    ],
    refs                      : [
        { ref: 'managerUI', selector: 'clientmanager' },
        { ref: 'clientsList', selector: 'clientmanager grid'},
        { ref: 'clientDetails', selector: 'clientmanager clientdetails'}
    ],
    stores                    : [
        'Recipients'
    ],
    init                      : function () {
        console.init('WMS.controller.manager.Recipients is initializing...');
        var me = this;
        me.control({
            'clientmanager toolbar button[itemId=newClient]'    : {
                'click': me.onNewClientAddClick
            },
            'clientmanager toolbar button[itemId=removeClient]' : {
                'click': me.onRemoveClientClick
            },
            'clientmanager toolbar button[itemId=editClient]'   : {
                'click': me.onEditClientClick
            },
            'clientmanager toolbar button[itemId=detailsClient]': {
                'click': me.onDetailsClientClickButton
            },
            'clientmanager toolbar button[itemId=releaseClient]': {
                'click': me.onReleaseClientClick
            },
            'clientmanager grid'                                : {
                'itemdblclick': me.onDetailsClientClickGrid
            }
        });
    },
    onDetailsClientRequest    : function (client) {
        var me = this,
            detailsView = me.getClientDetails(),
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
            store = me.getClientsStore(),
            selection = this.getSelectedClients();
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
            store = me.getClientsStore(),
            selection = this.getSelectedClients();
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
    },
    /**
     * Utility method returning selected clients.
     * Selection is understood as rows being currently selected
     * in the clients list
     * @return {*}
     */
    getSelectedClients        : function () {
        var me = this;
        return me.getClientsList().getView().getSelectionModel().getSelection();
    },
    popupDetailsView          : function () {
        var me = this,
            managerUI = me.getManagerUI(),
            detailsView = managerUI['items'].get('clientDetailed');
        if (Ext.isDefined(detailsView)) {
            detailsView.expand();
        } else {
            console.log('manager.Recipients :: Failed to popup with statistics')
        }
    }
});