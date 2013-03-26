/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

Ext.define('WMS.controller.Master', function () {
    var mask = undefined,
        /**
         * Small helper that search for tab [itemId]
         * tab in Ext.tab.Panel children,
         * and if tab is found displays it
         */
            openTab = function (view, tab) {
            setTimeout(function () {
                tab = view.child(tab);
                if (tab === view.getActiveTab()) {
                    return;
                }
                if (Ext.isDefined(tab)) {
                    tab.tab.show();
                    view.setActiveTab(tab);
                }
            }, 100);
        },
        setLoadMask = function (mask) {
            this.mask = mask;
        },
        getLoadMask = function () {
            return this.mask;
        },
        maskViewport = function (view) {
            if (Ext.isDefined(view)) {
                setLoadMask(new Ext.LoadMask(view.el, {
                    id : 'viewportMask',
                    msg: 'Loading content...'
                }));
                getLoadMask().show();
                console.log('Master :: Batman mode...');
            } else {
                console.log('Master :: Can not mask viewport, view undefined');
            }
        },
        unmaskViewport = function () {
            var mask = getLoadMask();

            if (!Ext.isDefined(mask)) {
                console.log('Master :: Looking mask via id property');
                mask = Ext.getCmp('viewportMask');
            }

            console.log('Master :: Took off the mask');
            mask.hide();
        },
        retrievePrincipal = function (callback, scope) {
            Ext.Ajax.request({
                url    : 'wms/principal/retrieve',
                timeout: 2000,
                method : 'GET',
                success: function (response) {
                    var obj = Ext.decode(response.responseText),
                        user = obj['data'][0];
                    console.dir(user);
                    callback.apply(scope, [user]);
                },
                failure: function (response) {
                    console.log('server-side failure with status code ' + response.status);
                }
            });
        };
    return {
        extend                : 'Ext.app.Controller',
        uses                  : [
            'WMS.view.manager.recipient.Manager', 'WMS.view.manager.supplier.Manager'
        ],
        refs                  : [
            {  ref: 'masterView', selector: 'masterview' }
        ],
        stores                : [
            'Companies'
        ],
        config                : {
            tabs: []
        },
        init                  : function () {
            console.init('WMS.controller.Master initializing...');
            var me = this;
            me.control({
                '#viewport': {
                    'afterrender': me.loadContent
                }
            });
            me.setTabs([]);
        },
        loadContent           : function (view) {
            var me = this,
                companies = me.getCompaniesStore(),
                toolbarCtrl = me.getController('WMS.controller.Toolbars')

            console.log('Master:: Pre-loading content...');

            maskViewport(view);

            companies.load({
                callback: function (data) {
                    if (Ext.isArray(data) && data.length >= 1) {
                        console.log('Master:: ' + Ext.String.format('Located {0} compan{1}', data.length, (data.length === 1
                            ? 'y' : 'ies')));
                    } else {
                        console.log('Login :: No suitable company found, commencing wizard');
                        me.openCompanyWizard();
                    }
                    retrievePrincipal(toolbarCtrl.setLoggedUserInformation, toolbarCtrl);
                    unmaskViewport();
                }
            });
        },
        openWarehousePreview  : function () {
            console.log('Master :: Opening warehouse overview');
            var me = this;
            openTab(me.getMasterView(), '#wmsOverview');
        },
        openWarehouseStatistic: function () {
            console.log('Master :: Opening warehouse statistics');
            var me = this;
            openTab(me.getMasterView(), '#wmsStatistics');
        },
        openWarehouseUnits    : function () {
            console.log('Master :: Opening warehouse units');
            var me = this;
            openTab(me.getMasterView(), '#wmsUnit');
        },
        openRecipientManager  : function () {
            console.log('Master :: Opening recipients manager');
            var me = this;
            me.openManager('WMS.view.manager.recipient.Manager');
        },
        openSupplierManager   : function () {
            console.log('Master :: Opening suppliers manager');
            var me = this;
            me.openManager('WMS.view.manager.supplier.Manager');
        },
        openCompanyWizard     : function () {
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
        openManager           : function (view) {
            var me = this,
                masterView = me.getMasterView(),
                manager = undefined,
                tabs = me.getTabs();

            if (Ext.Array.indexOf(tabs, view) < 0) {

                manager = me.getView(view).create();
                manager = masterView.add(manager);

                me.mon(manager, 'close', me.onTabClose, me);
                tabs.push(view);

                manager.show();
            }
        },
        onTabClose            : function (panel) {
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
    }
});
