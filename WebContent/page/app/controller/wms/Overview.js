/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 16:42
 */

Ext.define('WMS.controller.wms.Overview', {
    extend: 'Ext.app.Controller',

    stores: ['Units'],
    views : ['wms.Overview'],
    refs  : [
        {ref: 'unitsGrid', name: 'UnitsGrid'}
    ],

    init: function () {
        console.init('WMS.controller.wms.Overview initializing...');
        var me = this;

        me.getUnitsStore().addListener('load', function () {
            console.log('WMS.controller.wms.Overview:: Loaded units store');
        });
    }
});