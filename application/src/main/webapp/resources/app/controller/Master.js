/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

Ext.define('WMS.controller.Master', {
    extend              : 'Ext.app.Controller',
    uses                : [
        'WMS.view.manager.recipient.Manager', 'WMS.view.manager.supplier.Manager'
    ],
    refs                : [
        {  ref: 'masterView', selector: 'masterview' }
    ],
    stores:               [
      'Companies'
    ],
    config              : {
        tabs    : [],
        loadMask: undefined
    },
    init                : function () {
        console.init('WMS.controller.Master initializing...');
        var me = this;
        me.control({
            '#viewport': {
                'afterrender': me.loadContent
            }
        });
        me.setTabs([]);
    },
    loadContent      : function () {
            var me = this,
                companies = me.getCompaniesStore();

            console.log('Master:: Pre-loading content...');

            companies.load({
                callback: function (data) {
                    if (Ext.isArray(data) && data.length >= 1) {
                        console.log('Login :: ' + Ext.String.format('Located {0} compan{1}', data.length, (data.length === 1
                            ? 'y' : 'ies')));
                    } else {
                        console.log('Login :: No suitable company found, commencing wizard');
                        me.openCompanyWizard();
                    }
                }
            });
        },
    openRecipientManager: function () {
        console.log('Master :: Opening recipients manager');
        var me = this;
        me.openManager('WMS.view.manager.recipient.Manager');
    },
    openSupplierManager : function () {
        console.log('Master :: Opening suppliers manager');
        var me = this;
        me.openManager('WMS.view.manager.supplier.Manager');
    },
    openCompanyWizard   : function () {
        var me = this,
            companyWizardCtrl = me.getController('WMS.controller.wizard.Company');

        if (Ext.isDefined(companyWizardCtrl)) {
            companyWizardCtrl.openWizard();
        }
    },
    //  --- private --- ... //
    /**
     * @private
     * @description Open desired manager
     * @param view
     */
    openManager         : function (view) {
        var me = this, masterView = me.getMasterView(), manager = undefined, tabs = me.getTabs();

        if (Ext.Array.indexOf(tabs, view) < 0) {

            manager = me.getView(view).create();
            manager = masterView.add(manager);

            me.mon(manager, 'close', me.onTabClose, me);
            tabs.push(view);

            manager.show();
        }
    },
    onTabClose          : function (panel) {
        var me = this, tabs = me.getTabs(), className = Ext.ClassManager.getName(panel), index = Ext.Array.indexOf(tabs, className);

        if (index >= 0) {
            me.mun(panel, 'close', me.onTabClose);
            Ext.Array.erase(tabs, index, 1);
        } else {
            Ext.Error.raise({
                msg: 'Closing event was caught, but panel was not found in master tabs array'
            });
        }
    }
});
