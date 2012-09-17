/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 15.09.12
 * Time   : 20:21
 * File   : WMSHeader
 * Package: controller.toolbar
 * Created: 15-09-2012
 */

Ext.define('WMS.controller.WMSToolbars', {
    extend: 'Ext.app.Controller',
    views : [
        'toolbar.WMSHeader',
        'toolbar.WMSFooter'
    ],
    refs  : [
        {
            ref     : 'tBar',
            selector: 'wmstbar'
        }                    ,
        {
            ref     : 'fBar',
            selector: 'wmsfbar'
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
        console.log('Save action called, to be implemented');
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
        console.log('WMS.controller.WMSToolbars is ready');
        var me = this;

        function onButtonClick(button) {
            var itemId = button.getItemId();
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
                    console.log('Save button');
                } else if (itemId === 'refreshButton') {
                    console.log('Refresh button');
                }
            }
        }

        this.control({
            '#headerToolbar > button'       : {
                'click': onButtonClick
            },
            '#footerToolbar > button'       : {
                'click': onButtonClick
            },
            '#headerToolbar > button > menu': {
                'click': function (menu, item) {
                    console.log(Ext.String.format('Menu {0} item clicked', menu['id']));
                    onButtonClick(item);
                }
            }
        }, this);
    }
});