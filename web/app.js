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
            'Ext.ux'       : 'app/ux',
            'Ext.ux.menu'  : 'app/ux',
            'Ext.ux.layout': 'app/ux'
        }
    });

    Ext.override('Ext.panel.Panel', {
        titleAlign: 'center',
        style     : {
            marginBottom: '5px'
        }
    });

    Ext.Loader.loadScript({
        url   : 'app/ux/WMSProxy.js',
        onLoad: function () {
            Ext.application({
                name              : 'WMS',
                appFolder         : 'app',
                enableQuickTips   : true,
                autoCreateViewport: true,
                controllers       : [
                    // viewport WMSView underlying controller
                    'wms.Overview',
                    'wms.Statistics',
                    'wms.Unit',
                    'wms.unit.Canvas',
                    'wms.unit.Inventory',

                    'manager.Suppliers',
                    'manager.Recipients',

                    'wizard.Client',
                    'wizard.Company',

                    'Toolbars',
                    'Login',
                    'Master'
                ]
            });
        }
    });

}());
