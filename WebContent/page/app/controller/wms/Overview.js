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
            'wmsoverviews #add'      : {
                click: me.onNewUnit
            },
            'wmsoverviews #delete'   : {
                click: me.onUnitDelete
            },
            'wmsoverviews #unitsGrid': {
                'selectionchange': me.onUnitSelectionChanged
            }
        });

        unitsStore.addListener('load', function () {
            console.log('WMS.controller.wms.Overview:: Loaded units store');
        });
    },

    onUnitSelectionChanged: function (selModel, selections) {
        var grid = this.getUnitsGrid();
        grid.down('#delete').setDisabled(selections.length === 0);
    },

    onNewUnit: function () {
        var me = this,
            store = me.getUnitsStore(),
            grid = me.getUnitsGrid();

        store.insert(0, new WMS.model.entity.Unit());
        grid.getPlugin('unitRowEditor').startEdit(0, 0);

        Ext.getCmp('statusBar').setStatus({
            text : 'You\'ve just added new unit...',
            clear: {
                wait       : 10000,
                anim       : true,
                useDefaults: false
            }
        });
    },

    onUnitDelete: function () {
        var me = this,
            store = me.getUnitsStore(),
            grid = me.getUnitsGrid(),
            selection = grid.getView().getSelectionModel().getSelection();

        if (selection) {
            store.remove(selection);
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('You\'ve just deleted {0} {1}',
                    selection.length,
                    selection.length > 0 ? 'units' : 'unit'),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    }
});