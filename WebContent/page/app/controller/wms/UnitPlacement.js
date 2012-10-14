/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 14.10.12
 * Time  : 22:56
 */

Ext.define('WMS.controller.wms.UnitPlacement', {
    extend: 'Ext.app.Controller',
    views : ['WMS.view.wms.UnitPlacement'],

    refs: [
        { ref: 'unitBoard', selector: 'wmsunitplacement unitsDrawingCmp' }
    ],

    constructor: function (config) {
        config = Ext.apply({
            surface: []
        }, config);
        Ext.apply(this, config);
        this.callParent(arguments);
    },

    init: function () {
        console.init('WMS.controller.wms.UnitPlacement initializing');
        var me = this,
            warehouses = me.getController('Master').getWarehousesStore();

        me.control({
            'wmsunitplacement #unitsDrawingCmp': {
                render: function (board) {
                    console.log('UnitPlacement :: Drawing chart initialized');
                    me.drawUnits(board);
                }
            }
        });

        me.mon(warehouses, 'activechanged', function (store, activeWarehouse) {
            activeWarehouse.getUnits().addListener('load', function (store) {
                if (store.getTotalCount() > 0) {
                    me.preparePaths(store);
                }
            })
        });
    },

    drawUnits: function (board) {
        console.log('UnitPlacement :: Commencing sprites drawing...');
        var me = this,
            surface = board['surface'];

        me.surface = surface.add(me.surface);
        Ext.each(me.surface, function (sprite) {
            sprite.show(true);
        });
    },

    preparePaths: function (unitsStore) {
        console.log('UnitPlacement :: Commencing sprites initializing...');
        var unitName = undefined,
            unitSize = undefined,
            unitType = undefined,
            surface = new Array();

        var x = 120, y = 200;

        unitsStore.each(function (unit) {
            unitName = unit.get('name');
            unitSize = unit.get('size');
            unitType = unit.getType().get('name');

            surface.push({
                type          : 'rect',
                width         : 80,
                height        : 80,
                radius        : 15,
                fill          : 'green',
                opacity       : 0.5,
                stroke        : 'red',
                'stroke-width': 2,
                x             : x,
                y             : y
            });
            surface.push({
                type: 'text',
                text: unitName + '\n[ ' + unitSize + ' ]',
                font: '12px monospace',
                x   : x + 10,
                y   : y + 40
            });

            x += 120;
            y += 20;
        });
        this.surface = surface;
    }
});