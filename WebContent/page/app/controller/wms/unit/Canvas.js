/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 14.10.12
 * Time  : 22:56
 */

Ext.define('WMS.controller.wms.unit.Canvas', {
    extend: 'Ext.app.Controller',

    requires: [
        'WMS.controller.wms.unit.UnitDDManager'
    ],
    views   : [
        'WMS.view.wms.unit.Canvas'
    ],
    refs    : [
        { ref: 'unitBoard', selector: 'wmsunitcanvas unitsDrawingCmp' }
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
            },
            ddManager        : Ext.create('WMS.controller.wms.unit.UnitDDManager')
        }, config);
        Ext.apply(this, config);
        this.callParent(arguments);
    },

    init: function () {
        console.init('WMS.controller.wms.unit.Canvas initializing');
        var me = this;
        me.control({
            'wmsunitcanvas #unitsDrawingCmp': {
                boxready: me.onBoxReady
            }
        }, me);
    },

    onBoxReady: function (board) {
        console.log('Canvas :: Drawing chart initialized');
        var me = this;

        me.drawHostTiles(board);
        me.drawUnits(board);
    },

    drawHostTiles: function (board) {
        // TODO get paper size, to calculate tiles count on both axis
        // each tile can host only one unit
        console.log('Canvas :: Drawing - > host tiles');
        var me = this,
            surface = board['surface'],
            boardSize = board.getSize(),
            unitsCount = me.getController('Master').getWarehousesStore().getActive().getUnits().getTotalCount(),
            xCount = Math.floor(boardSize['width'] / me['drawConfiguration']['tile']['width']),
            yCount = Math.floor(boardSize['height'] / me['drawConfiguration']['tile']['height']),
            tile = undefined;

        if (!Ext.isDefined(surface)) {
            console.error('Canvas :: Surface not ready...');
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
                draggable     : true
            });
            var textShape = surface.add({
                type     : 'text',
                text     : unitRecord.get('name') + '\n[' + unitRecord.get('size') + ']',
                fill     : 'black',
                font     : '10px monospace',
                width    : unitWidth,
                height   : unitHeight,
                x        : Math.floor(rectShape['x'] + (rectShape['width'] / 3)),
                y        : Math.floor(rectShape['y'] + (rectShape['height'] / 4)),
                draggable: true
            });
            rectShape.show(true);
            textShape.show(true);
            rectShape['text'] = textShape;
            return rectShape;
        }

        console.log('Canvas :: Commencing sprites drawing...');
        var me = this,
            surface = board['surface'],
            it = 0,
            warehouse = me.getController('Master').getWarehousesStore().getActive(),
            unitStore = warehouse.getUnits(),
            unitWidth = me['drawConfiguration']['unit']['width'],
            unitHeight = me['drawConfiguration']['unit']['height'];

        // TODO add support for embedding tile withing unit record !

        unitStore.each(function (unit) {
            me['tiles'][it++]['unit'] = unit.setSprite(
                drawUnitShape(unit, me['tiles'][it].getBBox())
            );
        });
        me.ddManager.setTiles(me['tiles']);
        console.log('Canvas :: Drawn units count = [' + me['tiles'].length + ']');
    }
});