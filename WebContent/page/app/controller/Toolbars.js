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
        }                    ,
        {
            ref     : 'fBar',
            selector: 'footbar'
        }
    ],

    onRequestFailure: function (buttonText, error) {
        Ext.MessageBox.show({
            title  : Ext.String.format('Request for {0} failed permanently..', buttonText),
            msg    : error,
            width  : 300,
            buttons: Ext.Msg.OKCANCEL,
            icon   : Ext.MessageBox.WARNING
        });
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
    },

    onServerRequestAction: function (button) {
        var me = this;
        Ext.Ajax.request({
            url    : 'wms',
            params : {
                page: button['hash']
            },
            method : 'POST',
            success: function (response) {
                try {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                } catch (error) {
                    me.onRequestFailure(button['text'], error);
                }
            },
            failure: function (response, opts) {
                console.log('server-side failure with status code ' + response.status);
            }
        });
    },

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

    onMenuButtonClickWrapper: function (menu, button) {
        var me = this;
        me.onToolbarButtonClick(button);
    },

    onToolbarButtonClick: function (button) {
        var me = this,
            itemId = button.getItemId();
        if (Ext.isDefined(button['hash'])) {
            me.onServerRequestAction(button);
        } else {
            if (itemId === 'receiptButton') {
                console.log('Receipt button');
            } else if (itemId == 'releaseButton') {
                console.log('Release button');
            } else if (itemId === 'helpButton') {
                console.log('Help button');
            } else if (itemId === 'saveButton') {
                console.log('Toolbars :: Save button');
                me.onSaveAction();
            } else if (itemId === 'refreshButton') {
                console.log('Refresh button');
            }
        }
    }
});
