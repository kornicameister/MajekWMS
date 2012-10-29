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
    extend: 'Ext.app.Controller',

    stores: [
        'Clients'
    ],
    refs  : [
        {
            ref     : 'wizard',
            selector: 'wizardclient'
        }
    ],

    init: function () {
        console.log('WMS.controller.wizard.Client initializing...');
        var me = this;

        me.control({
            'wizardclient button[itemId=submit]': {
                'click': me.onClientSubmit
            },
            'wizardclient button[itemId=cancel]': {
                'click': me.onCancel
            }
        })
    },

    onClientSubmit: function (button) {
        console.log('wizard.Client :: Submit button has been clicked...');
        var me = this,
            form = button.up('form')['form'],
            clients = me.getClientsStore();

        if (form.isValid()) {
            var client = clients.add(form.getValues())[0];
            if (Ext.isDefined(client)) {
                Ext.getCmp('statusBar').setStatus({
                    text : Ext.String.format('Klient {0} został pomyślnie dodany', client.get('name')),
                    clear: {
                        wait       : 10000,
                        anim       : true,
                        useDefaults: false
                    }
                });
            } else {
                form.reset();
            }
            console.log('wizard.Client :: Client has been successfully added...');
            me.getWizard().close();
        }

    },

    onCancel: function () {
        var me = this;
        me.getWizard().close();
    }
});