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
    extend  : 'Ext.app.Controller',
    requires: [
        'Ext.ux.menu.StoreMenu'
    ],
    views   : [
        'toolbar.Header',
        'toolbar.Footer'
    ],
    refs    : [
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
            selector: 'headbar storemenu'
        }
    ],

    init: function () {
        console.init('WMS.controller.Toolbars initializing...');
        var me = this;

        me.control({
            '#headerToolbar storemenu'                   : {
                'iclick': me.onUnitSelected
            },
            '#footerToolbar button[itemId=saveButton]'   : {
                'click': me.onSaveAction
            },
            '#footerToolbar button[itemId=refreshButton]': {
                'click': me.onRefreshAction
            }
        });
    },

    onUnitSelected: function (storemenu, item, storeItem) {

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
