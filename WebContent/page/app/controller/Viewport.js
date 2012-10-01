/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 16:41
 */

Ext.define('WMS.controller.Viewport', {
    extend:'Ext.app.Controller',

    requires:[
        'WMS.controller.WMSConfiguration',
        'WMS.controller.WMSToolbars'
    ],


    init: function(){
        console.log('controller.Viewport initializing...');
    }


}, function () {
    console.log('WMS.controller.ViewportController successfully defined');
});