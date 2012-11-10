/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 29.10.12
 * Time   : 00:18
 */

/**
 * @class WMS.controller.wizard.Client
 * @extends Ext.app.Controller
 * @author kornicameister
 * @description This class masters wizard that is used do create new client
 */
Ext.define('WMS.controller.wizard.Client', {
    extend         : 'Ext.app.Controller',
    views          : [
        'WMS.view.wizard.client.Dialog'
    ],
    stores         : [
        'Cities',
        'ClientTypes',
        'Suppliers',
        'Recipients'
    ],
    refs           : [
        { ref: 'wizard', selector: 'wizardclient'},
        { ref: 'clientTypeCombo', selector: 'wizardclient combo[itemId=typeId]'}
    ],
    statics        : {
        MODE       : {
            SUPPLIER : 1,
            RECIPIENT: 2
        },
        CURRENTMODE: 0
    },
    init           : function () {
        console.init('WMS.controller.wizard.Client initializing...');
        var me = this;

        me.control({
            'wizardclient'                    : {
                'afterrender': me.setModeInCombo
            },
            'clientform button[itemId=submit]': {
                'click': me.onClientSubmit
            },
            'clientform button[itemId=cancel]': {
                'click': me.onCancel
            }
        })
    },
    setModeInCombo : function () {
        var me = this,
            combo = me.getClientTypeCombo();

        combo.setValue(me.workingMode);
    },
    onClientSubmit : function (button) {
        console.log('wizard.Client :: Submit button has been clicked...');
        var me = this,
            form = button.up('form')['form'];

        if (form.isValid()) {
            var client = undefined;
            switch (WMS.controller.wizard.Client.CURRENTMODE) {
                case WMS.controller.wizard.Client.MODE.RECIPIENT:
                    client = me.getRecipientsStore().saveAssociatedClient(form.getValues());
                    break;
                case WMS.controller.wizard.Client.MODE.SUPPLIER:
                    client = me.getSuppliersStore().saveAssociatedClient(form.getValues());
                    break;
            }
            if (Ext.isDefined(client)) {
                Ext.getCmp('statusBar').setStatus({
                    text : Ext.String.format('Klient {0} został pomyślnie dodany', client.get('name')),
                    clear: {
                        wait       : 10000,
                        anim       : true,
                        useDefaults: false
                    }
                });
                form.reset();
            } else {
                form.reset();
            }
            console.log('wizard.Client :: Client has been successfully added...');
            if (Ext.isDefined(me.getWizard())) {
                me.getWizard().close();
            }
        }

    },
    onCancel       : function () {
        var me = this;
        me.getWizard().close();
    },
    openAsRecipient: function () {
        var me = this;
        me.openWizard(WMS.controller.wizard.Client.MODE.RECIPIENT);
    },
    openAsSupplier : function () {
        var me = this;
        me.openWizard(WMS.controller.wizard.Client.MODE.SUPPLIER);
    },
    openWizard     : function (mode) {
        var me = this,
            wizard = me.getView('wizard.client.Dialog');
        WMS.controller.wizard.Client.CURRENTMODE = mode;
        wizard.create().show();
    }
});