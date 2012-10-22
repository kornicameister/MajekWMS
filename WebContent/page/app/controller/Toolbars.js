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
    extend: 'Ext.app.Controller',
    views : [
        'toolbar.Header',
        'toolbar.Footer'
    ],
    refs  : [
        {
            ref     : 'tBar',
            selector: 'headbar'
        },
        {
            ref     : 'fBar',
            selector: 'footbar'
        },
        {
            ref     : 'unitMenu',
            selector: 'headbar unitMenu'
        }
    ],

    init: function () {
        console.init('WMS.controller.Toolbars initializing...');
        var me = this;

        me.control({
            '#headerToolbar > button'       : {
                'click': me.onToolbarButtonClick
            },
            '#footerToolbar > button'       : {
                'click': me.onToolbarButtonClick
            },
            '#headerToolbar > button > menu': {
                'click': me.onMenuButtonClickWrapper
            }
        }, me);
    },

    onSaveAction: function () {
        Ext.StoreManager.each(function (store) {
            if (store['autoSync'] !== true) {
                store.sync();
            }
        });
    },

    onRefreshAction: function () {
        console.log('Refresh action called, revert changes, reload from server');
    }
});
