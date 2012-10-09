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
        {ref: 'unitsGrid', selector: 'grid'}
    ],

    init: function () {
        console.init('WMS.controller.wms.Overview initializing...');
        var me = this,
            unitsStore = me.getUnitsStore();

        me.control({
            '#add'   : {
                click: me.onNewUnit
            },
            '#delete': {
                click: me.onUnitDelete
            }
        });

        unitsStore.addListener('load', function () {
            console.log('WMS.controller.wms.Overview:: Loaded units store');
        });
    },

    onNewUnit: function () {
        var me = this,
            store = me.getUnitsStore(),
            grid = me.getUnitsGrid();

        store.insert(0, new WMS.model.entity.Unit());
        grid.getPlugin('unitRowEditor').startEdit(0, 0);
    },

    onUnitDelete: function () {
        var me = this,
            store = me.getUnitsStore(),
            grid = me.getUnitsGrid();

        var selection = grid.getView().getSelectionModel().getSelection()[0];
        if (selection) {
            store.remove(selection);
        }
    }
});