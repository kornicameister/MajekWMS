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

    init: function () {
        console.log('WMS.controller.WMSToolbars is ready');
        console.log(this);
        this.control({
            '#receiptButton'  : {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#releaseButton'  : {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#warehouseButton': {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#unitsButton'    : {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#inventoryButton': {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#settingsButton': {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#helpButton': {
                'click': function (button) {
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#saveButton' : {
                'click': function(button){
                    console.log(button.getItemId() + ' button clicked');
                }
            },
            '#refreshButton' : {
                'click': function(button){
                    console.log(button.getItemId() + ' button clicked');
                }
            }
        }, this);
    }
});