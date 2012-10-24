/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 17:02
 */

Ext.define('WMS.controller.Login', {
    extend: 'Ext.app.Controller',

    requires: [
        'WMS.view.login.LoginPanel',
        'WMS.view.login.Form'
    ],

    views: [
        'login.LoginPanel'
    ],

    init: function () {
        console.init('WMS.controller.Login initializing...');
    }
});