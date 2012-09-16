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

    init: function () {
        console.log('WMS.controller.WMSToolbars is ready');
    }
});