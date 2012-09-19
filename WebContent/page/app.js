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
    Ext.Loader.setConfig({
        enabled: true,
        paths  : {
            'Ext.ux': 'page/app/ux'
        }
    });

    Ext.application({
        name              : 'WMS',
        appFolder         : 'page/app',
        enableQuickTips   : true,
        autoCreateViewport: true,

        controllers: [
            'WMSToolbars'
        ]
    });
}());