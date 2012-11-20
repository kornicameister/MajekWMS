/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 15.09.12
 * Time   : 20:21
 * File   : WMSHeader
 * Package: controller.toolbar
 * Created: 15-09-2012
 */

Ext.define('WMS.controller.Toolbars', {
    extend                  : 'Ext.app.Controller',
    views                   : [
        'toolbar.Header',
        'toolbar.Footer',
        'wizard.client.Dialog'
    ],
    refs                    : [
        { ref: 'tBar', selector: 'headbar' },
        { ref: 'fBar', selector: 'footbar' },
        { ref: 'unitMenu', selector: 'headbar storemenu' }
    ],
    init                    : function () {
        console.init('WMS.controller.Toolbars initializing...');
        var me = this;
        me.control({
            '#headerToolbar storemenu'                   : {
                'iclick': me.onUnitSelected
            },
            '#headerToolbar button[itemId=clientsButton]': {
                'click': me.onRecipientsManagerClick
            },
            '#headerToolbar button[itemId=suppliers]'    : {
                'click': me.onSuppliersManagerClick
            },
            '#headerToolbar menu[itemId=clientsMenu]'    : {
                'click': me.onClientMenuClick
            },
            '#headerToolbar menu[itemId=suppliersMenu]'  : {
                'click': me.onClientMenuClick
            },
            '#footerToolbar button[itemId=saveButton]'   : {
                'click': me.onSaveAction
            },
            '#footerToolbar button[itemId=refreshButton]': {
                'click': me.onRefreshAction
            }
        });
    },
    onClientMenuClick       : function (menu, item) {
        console.log('Toolbars :: ' + Ext.String.format('{0} button clicked...', item['itemId']));
        var me = this,
            clientWizardManager = me.getController('WMS.controller.wizard.Client');

        if (item['itemId'] === 'addClient') {
            clientWizardManager.openAsRecipient();
        } else if (item['itemId'] === 'addSupplier') {
            clientWizardManager.openAsSupplier();
        } else {
            console.log('Toolbars :: ' + Ext.String.format('{0} button has not ben recognized...', item['itemId']))
        }
    },
    onSuppliersManagerClick : function () {
        var me = this,
            masterCtrl = me.getController('WMS.controller.Master');
        masterCtrl.openSupplierManager();
    },
    onRecipientsManagerClick: function () {
        var me = this,
            masterCtrl = me.getController('WMS.controller.Master');
        masterCtrl.openRecipientManager();
    },
    onUnitSelected          : function (storemenu, item, storeItem) {

    },
    onSaveAction            : function () {
        Ext.StoreManager.each(function (store) {
            if (store['autoSync'] !== true) {
                store.sync();
            }
        });
    },
    onRefreshAction         : function () {
        console.log('Refresh action called, revert changes, reload from server');
    }
});