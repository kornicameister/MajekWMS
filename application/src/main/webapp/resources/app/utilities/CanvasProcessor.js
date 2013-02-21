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
        SPRITES = new Ext.util.MixedCollection(),
        USS = Ext.define('CanvasProcessorStorage', {
            extend      : 'Ext.data.Store',
            fields      : [
                'id', 'rect_id', 'text_id', 'unit_id', 'lock_id',
                { name: 'marked', type: 'boolean', defaultValue: false},
                { name: 'locked', type: 'boolean', defaultValue: true}
            ],
            sorters     : [
                {
                    property : 'rect_id',
                    direction: 'ASC'
                },
                {
                    property : 'text_id',
                    direction: 'ASC'
                },
                {
                    property : 'unit_id',
                    direction: 'ASC'
                }
            ],
            autoSync    : true,
            proxy       : {
                type: 'memory',
                id  : 'canvas_processor_storage'
            },
            findBySprite: function (sprite) {
                var self = this,
                    matchedSprite,
                    sprite_id = (Ext.isDefined(sprite['id']) ? sprite['id'] : sprite);

                if ((matchedSprite = self.findByRect(sprite_id)) !== null) {
                    return matchedSprite;
                } else if ((matchedSprite = self.findByText(sprite_id)) != null) {
                    return matchedSprite;
                } else {
                    return undefined;
                }
            },
            findByRect  : function (rect) {
                var self = this,
                    record = undefined;
                if (rect.indexOf('ext-') >= 0) {
                    record = self.findRecord('rect_id', rect);
                } else {
                    record = self.findRecord('rect_id', rect['id']);
                }
                return record;
            },
            findByText  : function (text) {
                var self = this,
                    record = undefined;
                if (text.indexOf('ext-') >= 0) {
                    record = self.findRecord('text_id', text);
                } else {
                    record = self.findRecord('text_id', text['id']);
                }
                return record;
            }
        }),
        clearCanvas = function () {

        },
        drawCanvas = function () {
            var stepResult = drawHostTiles();
            if (stepResult === true) {
                stepResult = drawUnitSprites();
            }
            if (stepResult === true) {
                stepResult = setSurfaceListeners();
            }
            return stepResult;
        },
        setSurfaceListeners = function () {
            var surface = board['surface'];

            if (!Ext.isDefined(surface)) {
                console.error('CanvasProcessor :: Global listeners -> setting failed, no surface...');
                return false;
            }

            me.mon(surface, 'mousemove', privateListeners['surfaceMousemove'], me);
            me.mon(board.getEl(), 'contextmenu', privateListeners['boardContextMenu'], me);

            return true;
        },
        drawUnitSprites = function () {
            //@TODO add reading cached drawing from database
            console.log('CanvasProcessor :: Drawing - > unit sprites -> in progress...');
            var self = me,
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
                    lockPart = surface.add({
                        type  : 'rect',
                        width : unitWidth / 5,
                        height: unitHeight / 5,
                        x     : rectX - 3,
                        y     : rectY - 3,
                        radius: 36,
                        stroke: 'red',
                        fill  : 'black'
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
                unitSprite.add(rectPart);
                unitSprite.add(textPart);
                unitSprite.add(lockPart);
                surface.add(unitSprite);

                // 5. attach listeners
                self.mon(unitSprite, 'mousedown', privateListeners.unitSpriteMousedown, self);
                self.mon(unitSprite, 'mouseup', privateListeners.unitSpriteMouseup, self);

                // 6. push them to array, fields ['id', 'rect_id', 'text_id', 'unit', 'marked']
                drawnUnitSprites.push({
                    id     : unitSprite['id'],
                    rect_id: rectPart['id'],
                    text_id: textPart['id'],
                    lock_id: lockPart['id'],
                    unit_id: unitRecord.getId()
                });
                SPRITES.add(unitSprite['id'], unitSprite);

                // 7. show them
                unitSprite.show(true);
            }, self);

            // 8. save them to local storage
            USS.add(drawnUnitSprites);

            console.log('CanvasProcessor :: Drawing - > unit sprites -> finished...');
            return true;
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
        releaseLockOnUnit = function (unit_id) {
            var spriteRecord = USS.findRecord('unit_id', unit_id),
                sprite = SPRITES.get(spriteRecord.getId()).getAt(0);

            spriteHandlers.changeLockStatus(spriteRecord);
        },
        privateListeners = {
            boardContextMenu   : function (event, target) {
                console.log('CanvasProcessor :: Lister -> board -> contextmenu -> triggered...\nevent=', event, '\ntarget=', target);
                var sprite_id = target['id'],
                    sprite = USS.findBySprite(sprite_id);

                event.preventDefault();
                if (Ext.isDefined(sprite)) {
                    spriteHandlers.selectSprite(sprite_id);
                    me.fireEvent('unitmenu', event['currentTarget'], event.getXY(), sprite.get('unit_id'));
                }
            },
            unitSpriteMousedown: function (event, htmlTarget) {
                console.log('CanvasProcessor :: Lister -> sprite -> mousedown -> triggered...');
            },
            unitSpriteMouseup  : function (event, htmlTarget) {
                console.log('CanvasProcessor :: Lister -> sprite -> mouseup -> triggered...');
            },
            surfaceMousemove   : function (event, htmlTarget) {
                console.log('CanvasProcessor :: Lister -> surface -> mousemove -> triggered...');
            }
        },
        spriteHandlers = {
            changeLockStatus: function (spriteRecord) {
                if (spriteRecord !== null && Ext.isDefined(spriteRecord)) {
                    var group = SPRITES.get(spriteRecord.getId()),
                        sprite = group.getAt(0),
                        lock = group.get(2);

                    if (spriteRecord.get('locked') === true) {
                        sprite.setAttributes({
                            scale: {
                                x: 1.2,
                                y: 1.2
                            }
                        });
                        lock.setAttributes({
                            fill: 'orange'
                        });
                        spriteRecord.set('locked', false);
                    } else {
                        sprite.setAttributes({
                            scale: {
                                x: 1,
                                y: 1
                            }
                        }, true);
                        lock.setAttributes({
                            fill: 'black'
                        });
                        spriteRecord.set('locked', true);
                    }

                    group.redraw(true);
                }
            },
            selectSprite    : function (sprite) {
                console.log('CanvasProcessor :: SpriteHandler -> sprite -> click -> triggered...\nsprite=', sprite);
                var record = USS.findBySprite(sprite),
                    markedRecord = USS.findRecord('marked', true),
                    markedSprite = null;
                if (!Ext.isDefined(record) || record === null) {
                    console.log('CanvasProcessor :: SpriteHandler -> sprite -> click -> record not found');
                }

                sprite = SPRITES.get(record.getId()).getAt(0);
                if (markedRecord !== null) {
                    markedSprite = SPRITES.get(markedRecord.getId()).getAt(0);
                }

                if (record.get('marked') === false) {
                    sprite.setAttributes({
                        fill: 'yellow'
                    }, true);
                    record.set('marked', true);

                    if (markedSprite !== null) {
                        markedSprite.setAttributes({
                            fill: 'green'
                        }, true);
                        markedRecord.set('marked', false);
                    }
                } else {
                    sprite.setAttributes({
                        fill: 'green'
                    }, true);
                    record.set('marked', false);
                }
            }
        };
    return {
        requires     : [
            'WMS.model.sprite.Unit'
        ],
        mixins       : {
            observable: 'Ext.util.Observable'
        },
        constructor  : function (config) {
            me = this;

            // init block
            me.mixins.observable.constructor.call(me, config);
            board = config['board'];
            sizes = config['sizes'];
            unitStore = config['unitStore'];
            USS = Ext.create('CanvasProcessorStorage');
            board.center();
            // init block

            me.addEvents(
                'unitclick',
                'unitmenu',
                'unitrelease'
            );

            console.log('CanvasProcessor :: init canvas=', board, ', sizes=', sizes);
            me.callParent(arguments)
        },
        draw         : function (redraw) {
            console.log('CanvasProcessor :: drawing in progress...');
            redraw = (Ext.isDefined(redraw) ? redraw : false);
            if (redraw === true) {
                clearCanvas();
            }
            var result = drawCanvas();
            console.log('CanvasProcessor :: drawing finished...');
            return result;
        },
        releaseLockOn: function (unit_id) {
            console.log('CanvasProcessor :: releasing lock on unit=', unit_id);
            var status;
            {
                status = releaseLockOnUnit(unit_id);
            }
            console.log('CanvasProcessor :: released lock on unit=', unit_id);
            return status;
        },
        lockOn       : function (unit_id) {
            console.log('CanvasProcessor :: locking unit=', unit_id);
            var status;
            {
                status = releaseLockOnUnit(unit_id);
            }
            console.log('CanvasProcessor :: locked unit=', unit_id);
            return status;
        }
    }
});