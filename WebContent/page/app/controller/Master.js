/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 28.09.12
 * Time   : 00:58
 * File   : WMSConfiguration
 * Package: controller
 * Created: 28-09-2012
 */

// TODO consider getting it fuck away !
Ext.define('WMS.controller.Master', {
    extend: 'Ext.app.Controller',

    views : [
        'Master'
    ],

    init: function () {
        console.init('WMS.controller.Master initializing...');
        var me = this;
        me.control({
            'masterview': {
                'afterrender': me.onMasterRender
            }
        });
    },

    onMasterRender: function (view) {
        console.log('Master :: Opening login.Dialog...');
        Ext.create('WMS.view.login.Dialog');
    }
});