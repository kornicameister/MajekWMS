/**
 * Created with IntelliJ IDEA.
 * User: kornicameister
 * Date: 20.02.13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */

Ext.define('WMS.utilities.CanvasProcessor', function () {
    var me = this,
        tilesSprites = [],
        board = undefined,
        sizes = undefined,
        unitStore = undefined,
        clearCanvas = function () {

        },
        drawCanvas = function () {
            var stepResult = drawHostTiles();
            if (stepResult == true) {
                stepResult = drawUnitSprites();
            }
            return stepResult;
        },
        drawUnitSprites = function () {
            //@TODO add reading cached drawing from database
            console.log('CanvasProcessor :: Drawing - > unit sprites -> in progress...');
            var self = me,
                unitSpriteStore = self.getSpritesStore(),
                drawnUnitSprites = [],
                surface = board['surface'],
                unitWidth = sizes['unit']['width'],
                unitHeight = sizes['unit']['height'];

            unitStore.each(function (unitRecord) {
                console.log(Ext.String.format('CanvasProcessor :: Drawing - > unit sprites -> rect for [ {0}/{1} ] unit',
                    unitRecord.getId(),
                    unitRecord.get('name')));

                var unitName = unitRecord.get('name'),
                    unitSize = unitRecord.get('size'),
                    tileBBox = tilesSprites[drawnUnitSprites.length].getBBox(),
                    rectX = Math.floor(tileBBox['x'] + ((tileBBox['width'] - unitWidth) / 2)),
                    rectY = Math.floor(tileBBox['y'] + ((tileBBox['height'] - unitWidth) / 2)),
                // 1. create rect shape
                    rectPart = surface.add({
                        type  : 'rect',
                        width : unitWidth,
                        height: unitHeight,
                        x     : rectX,
                        y     : rectY,
                        radius: 12,
                        fill  : 'green',
                        stroke: 'red'
                    }),
                // 2. create text shape
                    textPart = surface.add({
                        type  : 'text',
                        text  : unitName + '\n[' + unitSize + ']',
                        fill  : 'black',
                        font  : '10px monospace',
                        width : unitWidth,
                        height: unitHeight,
                        x     : Math.floor(rectX + (unitWidth / 3)),
                        y     : Math.floor(rectY + (unitHeight / 4))
                    }),
                // 3. prepare placeholder for them
                    unitSprite = Ext.create('Ext.draw.CompositeSprite', {
                        surface: surface
                    });

                // 4. save shapes onto the surface and into the group
                surface.add(unitRecord.getId(), rectPart);
                surface.add(unitRecord.getId(), textPart);

                // 5. attach listeners
                self.mon(rectPart, 'dblclick', privateListeners['dblclick'], self);
                self.mon(surface, 'mousedown', privateListeners['mousedown'], self);
                self.mon(surface, 'mouseup', privateListeners['mouseup'], self);

                // 6. push them to array
                drawnUnitSprites.push({
                    id    : unitSprite['id'],
                    sprite: unitSprite,
                    unit  : unitRecord
                });

                // 7. show them
                rectPart.show(true);
                textPart.show(true);
            }, self);

            // 8. save them to local storage
            unitSpriteStore.add(drawnUnitSprites);

            console.log('CanvasProcessor :: Drawing - > unit sprites -> finished...');
        },
        drawHostTiles = function () {
            console.log('CanvasProcessor :: Drawing - > host tiles -> in progress...');
            var boardSize = board.getSize(),
                unitsCount = unitStore.getTotalCount(),
                xCount = Math.floor(boardSize['width'] / sizes['tile']['width']),
                yCount = Math.floor(boardSize['height'] / sizes['tile']['height']),
                tile = undefined;

            if (!Ext.isDefined(board['surface'])) {
                console.error('CanvasProcessor :: Surface not ready...');
                return false;
            }

            if (xCount >= yCount) {
                xCount = Math.max(xCount, unitsCount);
            } else {
                yCount = Math.max(yCount, unitsCount);
            }

            for (var y = 0; y < yCount; y++) {
                for (var x = 0; x < xCount; x++) {
                    tile = board['surface'].add({
                        type          : 'rect',
                        width         : sizes['tile']['width'],
                        height        : sizes['tile']['height'],
                        radius        : 5,
                        fill          : '#C0C0C0',
                        stroke        : '#CCCCCC',
                        'stroke-width': 2,
                        x             : x * sizes['tile']['width'] + 5,
                        y             : y * sizes['tile']['height'] + 5,
                        opacity       : 0.2
                    });
                    tile.show(true);
                    tilesSprites.push(tile);
                }
            }
            console.log('CanvasProcessor :: Drawing - > host tiles -> finished...');
            return tilesSprites.length > 0;
        },
        privateListeners = {
            'dblclick' : function (event) {
                console.log('CanvasProcessor :: Lister -> dblclick -> triggered...')
            },
            'mousedown': function (event, htmlTarget) {
                console.log('CanvasProcessor :: Lister -> mousedown -> triggered...')
            },
            'mouseup'  : function (event, htmlTarget) {
                console.log('CanvasProcessor :: Lister -> mouseup -> triggered...')
            }
        };
    return {
        requires   : [
            'WMS.model.sprite.Unit'
        ],
        mixins     : {
            observable: 'Ext.util.Observable'
        },
        config     : {
            spritesStore: undefined
        },
        constructor: function (config) {
            me = this;

            me.mixins.observable.constructor.call(me, config);
            me.setSpritesStore(Ext.create('Ext.data.Store', {
                model   : 'WMS.model.sprite.Unit',
                autoLoad: true,
                autoSync: true
            }));
            board = config['board'];
            sizes = config['sizes'];
            unitStore = config['unitStore'];

            me.addEvents(
                'unitdblclick',
                'unitclick',
                'unitrelease'
            );

            console.log('CanvasProcessor :: init canvas=', board, ', sizes=', sizes);
            me.callParent(arguments)
        },
        draw       : function (redraw) {
            console.log('CanvasProcessor :: drawing in progress...');
            redraw = (Ext.isDefined(redraw) ? redraw : false);
            if (redraw === true) {
                clearCanvas();
            }
            var result = drawCanvas();
            console.log('CanvasProcessor :: drawing finished...');
            return result;
        }
    }
});