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
            tiles            : [],
            drawConfiguration: {
                tile: {
                    width : 120,
                    height: 120
                },
                unit: {
                    width : 80,
                    height: 80
                }
            }
        }, config);
        Ext.apply(this, config);
        this.callParent(arguments);
    },

    init: function () {
        console.init('WMS.controller.wms.UnitPlacement initializing');
        var me = this;
        me.control({
            'wmsunitplacement #unitsDrawingCmp': {
                boxready: me.onUnitBoardRender
            }
        }, me);
    },

    onUnitBoardRender: function (board) {
        console.log('UnitPlacement :: Drawing chart initialized');
        var me = this;

        me.drawHostTiles(board);
        me.drawUnits(board);
    },

    drawHostTiles: function (board) {
        // TODO get paper size, to calculate tiles count on both axis
        // each tile can host only one unit
        console.log('UnitPlacement :: Drawing - > host tiles');
        var me = this,
            surface = board['surface'],
            boardSize = board.getSize(),
            unitsCount = unitStore = me.getController('Master').getWarehousesStore().getActive().getUnits().getTotalCount(),
            xCount = Math.floor(boardSize['width'] / me['drawConfiguration']['tile']['width']),
            yCount = Math.floor(boardSize['height'] / me['drawConfiguration']['tile']['height']),
            tile = undefined;

        if (!Ext.isDefined(surface)) {
            console.error('UnitPlacement :: Surface not ready...');
            return;
        }

        if (xCount >= yCount) {
            xCount = Math.max(xCount, unitsCount);
        } else {
            yCount = Math.max(yCount, unitsCount);
        }

        for (var y = 0; y < yCount; y++) {
            for (var x = 0; x < xCount; x++) {
                tile = surface.add({
                    type   : 'rect',
                    width  : me['drawConfiguration']['tile']['width'],
                    height : me['drawConfiguration']['tile']['height'],
                    radius : 5,
                    fill   : '#C0C0C0',
                    x      : x * me['drawConfiguration']['tile']['width'] + 5,
                    y      : y * me['drawConfiguration']['tile']['height'] + 5,
                    opacity: 0.2
                });
                tile.show(true);
                me.tiles.push(tile);
            }
        }
    },

    drawUnits: function (board) {
        console.log('UnitPlacement :: Commencing sprites drawing...');
        var me = this,
            surface = board['surface'],
            it = 0,
            tileBBox,
            rectShape,
            rectText,
            unitStore = me.getController('Master').getWarehousesStore().getActive().getUnits();

        unitStore.each(function (unit) {
            tileBBox = me['tiles'][it].getBBox();
            rectShape = surface.add({
                type          : 'rect',
                width         : me['drawConfiguration']['unit']['width'],
                height        : me['drawConfiguration']['unit']['height'],
                radius        : 15,
                fill          : 'green',
                stroke        : 'red',
                'stroke-width': 2,
                x             : tileBBox['x'] + tileBBox['width'] / 4,
                y             : tileBBox['y'] + tileBBox['height'] / 4
            });
            rectText = surface.add({
                type: 'text',
                text: unit.get('name') + '\n[ ' + unit.get('size') + ' ]',
                font: '12px monospace',
                x   : tileBBox['x'] + tileBBox['width'] / 4 + 10,
                y   : tileBBox['y'] + tileBBox['height'] / 4 + 40
            });
            me['tiles'][it]['unit'] = {
                shape : rectShape,
                header: rectText
            };

            rectShape.show(true);
            rectText.show(true);

            console.log('UnitPlacement :: Tile assigned with unit -> ', me['tiles'][it++]);
        });
    }
});