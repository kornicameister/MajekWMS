/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 14.10.12
 * Time  : 22:56
 */

Ext.define('WMS.controller.wms.unit.Canvas', {
    extend: 'Ext.app.Controller',

    stores: [
        'UnitSprites'
    ],
    views : [
        'WMS.view.wms.unit.Canvas'
    ],
    refs  : [
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
            }
        }, config);
        Ext.apply(this, config);
        this.callParent(arguments);
    },

    init: function () {
        console.init('WMS.controller.wms.unit.Canvas initializing... ');
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
            var rectShape = board['surface'].add({
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
            var textShape = board['surface'].add({
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
            units = me.getController('Master').getWarehousesStore().getActive().getUnits(),
            unitSprites = me.getUnitSpritesStore(),
            unitWidth = me['drawConfiguration']['unit']['width'],
            unitHeight = me['drawConfiguration']['unit']['height'],
            unitSprite = undefined,
            sprites = [];

        units.each(function (unit) {
            unitSprite = drawUnitShape(unit, me['tiles'][sprites.length].getBBox());
            me.mon(unitSprite, 'dblclick', me.onUnitSelected, me);
            unitSprite = Ext.create('WMS.model.sprite.Unit', {
                id     : unitSprite['id'],
                sprite : unitSprite,
                unit_id: unit.getId()
            });
            sprites.push(unitSprite);
        });
        unitSprites.add(sprites);
        console.log('Canvas :: Drawn units count = [' + me['tiles'].length + ']');
    },

    onUnitSelected: function (eventTarget) {
        console.log('UnitDDManager :: Unit has been selected, loading products in progress...');
        var me = this,
            unitStore = me.getStore('Units');

        var unit = unitStore.getById(
                me
                    .getStore('UnitSprites')
                    .getById(eventTarget['id'])
                    .get('unit_id')
            ),
            controller = me.getController('wms.unit.Inventory');

        unitStore.setActive(unit);
        controller.loadProductsFromUnit(unit);
    }
});