/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

Ext.define('WMS.controller.WMSConfiguration', {
    extend: 'Ext.app.Controller',

    stores: [
        'WMSConfiguration'
    ],

    init: function () {
        console.init('WMS.controller.WMWConfiguration initializing...');
        var me = this;

        me.getWMSConfigurationStore().addListener('load', function (confStore) {
            var warehouses = confStore.getWarehouses();
            if (warehouses.length === 0) {
                Ext.MessageBox.show({
                    title        : 'No warehouse found...',
                    msg          : 'MajekWMS detected that' +
                        ' there is no warehouse defined\n' +
                        'Would you like to create one now ?',
                    buttons      : Ext.MessageBox.YESNO,
                    fn           : me.openWarehouseWizard,
                    animateTarget: Ext.getBody(),
                    icon         : Ext.MessageBox.QUESTION,
                    scope        : me
                });
            }
        });
    },

    /**
     * Method called after message box informing user of no warehouse
     * if closed either with Yes or No button
     * @param answer
     */
    openWarehouseWizard: function (answer) {
        if (answer === 'yes') {
            var wWizard = Ext.create('WMS.view.wizard.Warehouse', {
                autoShow: true
            });
            console.log(wWizard);
        }
    }
});