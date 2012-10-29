/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 13.09.12
 * Time   : 23:31
 * File   : app
 * Created: 13-09-2012
 */

// entry point for file
(function () {

    console.init = function (str) {
        console.log('INIT :: ' + str);
    };

    Ext.Loader.setConfig({
        enabled: true,
        paths  : {
            'Ext.ux'     : 'page/app/ux',
            'Ext.ux.menu': 'page/app/ux'
        }
    });

    Ext.Loader.loadScript({
        url   : 'page/app/ux/WMSProxy.js',
        onLoad: function () {
            Ext.application({
                name              : 'WMS',
                appFolder         : 'page/app',
                enableQuickTips   : true,
                autoCreateViewport: true,

                controllers: [
                    // viewport WMSView underlying controller
                    'wms.Overview',
                    'wms.Statistics',
                    'wms.Unit',
                    'wms.unit.Canvas',
                    'wms.unit.Inventory',

                    'wizard.Client',
                    'wizard.Warehouse',

                    'Toolbars',
                    'Login',
                    'Master'
                ]
            });
        }
    });

}());
