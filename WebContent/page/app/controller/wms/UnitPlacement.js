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
                    width : 90,
                    height: 90
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
            unitsCount = me.getController('Master').getWarehousesStore().getActive().getUnits().getTotalCount(),
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
                    type          : 'rect',
                    width         : me['drawConfiguration']['tile']['width'],
                    height        : me['drawConfiguration']['tile']['height'],
                    radius        : 5,
                    fill          : '#C0C0C0',
                    stroke        : '#CCCCCC',
                    'stroke-width': 2,
                    x             : x * me['drawConfiguration']['tile']['width'] + 5,
                    y             : y * me['drawConfiguration']['tile']['height'] + 5,
                    opacity       : 0.2
                });
                tile.show(true);
                me['tiles'].push(tile);
            }
        }
    },

    drawUnits: function (board) {
        function drawUnitShape(unitRecord, tileBBox) {
            var rectShape = surface.add({
                type          : 'rect',
                width         : unitWidth,
                height        : unitHeight,
                x             : Math.floor(tileBBox['x'] + ((tileBBox['width'] - unitWidth) / 2)),
                y             : Math.floor(tileBBox['y'] + ((tileBBox['height'] - unitWidth) / 2)),
                radius        : 12,
                fill          : 'green',
                stroke        : 'red',
                'stroke-width': 2,
                group         : warehouse.get('name')
            });
            var textShape = surface.add({
                type  : 'text',
                text  : unitRecord.get('name') + '\n[' + unitRecord.get('size') + ']',
                fill  : 'black',
                font  : '10px monospace',
                width : unitWidth,
                height: unitHeight,
                x     : Math.floor(rectShape['x'] + (rectShape['width'] / 3)),
                y     : Math.floor(rectShape['y'] + (rectShape['height'] / 4))
            });
            rectShape.show(true);
            textShape.show(true);
            rectShape['description'] = textShape;
            console.log('UnitPlacement :: Drawn UnitShape -> ', rectShape);
            return rectShape;
        }

        console.log('UnitPlacement :: Commencing sprites drawing...');
        var me = this,
            surface = board['surface'],
            it = 0,
            warehouse = me.getController('Master').getWarehousesStore().getActive(),
            unitStore = warehouse.getUnits(),
            unitWidth = me['drawConfiguration']['unit']['width'],
            unitHeight = me['drawConfiguration']['unit']['height'];

        unitStore.each(function (unit) {
            me['tiles'][it]['unit'] = drawUnitShape(unit, me['tiles'][it].getBBox());
            it++;
        });
    }
});