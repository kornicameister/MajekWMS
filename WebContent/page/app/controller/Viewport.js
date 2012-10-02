/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 16:41
 */

Ext.define('WMS.controller.Viewport', {
    extend: 'Ext.app.Controller',

    views: [
        'Viewport'
    ],

    init: function () {
        console.init('WMS.controller.Viewport initializing...');
        console.log(this.getStore('Warehouses'));
    }


});