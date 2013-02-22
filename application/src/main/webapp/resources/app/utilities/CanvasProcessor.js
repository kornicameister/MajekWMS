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
        SDU = Ext.define(null, function (SpriteDraggingUtility) {
            return {

            }
        }),
        SLU = Ext.define(null, function (SpriteLockingUtility) {
            var _unlockedSpriteId = undefined,
                _spriteAnimationsConfig = {
                    lockAnim          : {
                        to      : {
                            fill            : "#FFFF00",
                            "stroke-width"  : 4,
                            "stroke-opacity": 0.5
                        },
                        duration: 2500
                    },
                    unlockAnim        : {
                        to      : {
                            fill            : "#000000",
                            "stroke-width"  : 1,
                            "stroke-opacity": 1
                        },
                        duration: 2500
                    },
                    disabledSpriteAnim: {
                        to      : {
                            zIndex : 1,
                            opacity: 0.3
                        },
                        duration: 2500
                    },
                    enabledSpriteAnim : {
                        to      : {
                            zIndex : 2,
                            opacity: 1
                        },
                        duration: 2500
                    }
                },
                _spriteAnimators = {
                    setLockedOffSpriteView : function (spriteRecord) {
                        if (spriteRecord !== null && Ext.isDefined(spriteRecord)) {
                            var group = SPRITES.get(spriteRecord.getId()),
                                lock = group.get(2);
                            lock.animate(_spriteAnimationsConfig.lockAnim);
                        }
                    },
                    disableSpritesExceptFor: function (spriteRecord) {
                        var nonSelected = SPRITES.filter(new Ext.util.Filter({
                            filterFn: function (item) {
                                return item['id'] !== spriteRecord.getId()
                            }
                        }), spriteRecord.getId());
                        nonSelected.eachKey(function (spriteId, sprite) {
                            sprite.animate(_spriteAnimationsConfig.disabledSpriteAnim);
                        });
                    },
                    resetSpritesToBasicView: function () {
                        SPRITES.eachKey(function (spriteId, sprite) {
                            sprite.animate(_spriteAnimationsConfig.enabledSpriteAnim);
                            sprite.getAt(2).animate(_spriteAnimationsConfig.unlockAnim);
                        });
                    }
                };
            return {
                statics: {
                    /**
                     * Quite cool method that wraps and utilise operation that results
                     * in masking all sprites except for the the one passed as argument.
                     *
                     * @param spriteRecord that is to be locked off
                     * @return the same record as begin passed
                     */
                    lockOff            : function (spriteRecord) {
                        if (spriteRecord.get('locked') === true) {
                            console.log('SpriteLockingUtility :: Locking [ OFF ] spriteRecord=', spriteRecord);
                            _spriteAnimators.setLockedOffSpriteView(spriteRecord);
                            _spriteAnimators.disableSpritesExceptFor(spriteRecord);
                            _unlockedSpriteId = spriteRecord.getId();
                        } else {
                            console.log('SpriteLockingUtility :: Could not [ DISABLE ] locking on spriteRecord=', spriteRecord);
                        }
                        return spriteRecord;
                    },
                    /**
                     * Does the same as lockOff but in opposite way
                     *
                     * @param spriteRecord that is to be locked off
                     * @return the same record as begin passed
                     */
                    lockOn             : function (spriteRecord) {
                        if (spriteRecord.get('locked') === false) {
                            console.log('SpriteLockingUtility :: Locking [ ON ] spriteRecord=', spriteRecord);
                            _spriteAnimators.resetSpritesToBasicView();
                            _unlockedSpriteId = undefined;
                        } else {
                            console.log('SpriteLockingUtility :: Could not [ ENABLE ] locking on spriteRecord=', spriteRecord);
                        }
                        return spriteRecord;
                    },
                    getUnlockedSpriteId: function () {
                        return _unlockedSpriteId;
                    },
                    isLockOn           : function () {
                        return Ext.isDefined(_unlockedSpriteId);
                    },
                    isLocked           : function (sprite) {
                        var isDefined = SLU.isLockOn();
                        if (isDefined === true) {
                            isDefined = sprite.getId() === _unlockedSpriteId;
                        }
                        return isDefined
                    }
                }
            }
        }),
        CPS = Ext.define('CanvasProcessorStorage', {
            extend      : 'Ext.data.Store',
            fields      : [
                'id', 'rect_id', 'text_id', 'unit_id', 'lock_id',
                { name: 'marked', type: 'boolean', defaultValue: false},
                // locked = true, sprite can not be dragged
                // locked = false => unlocked, sprite can be dragged
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
        SCD = Ext.define(null, function (SpriteCanvasDrawer) {
            return {
                statics: {
                    clearCanvas    : function () {

                    },
                    drawUnitSprites: function () {
                        //@TODO add reading cached drawing from database
                        console.log('SpriteCanvasDrawer :: Drawing - > unit sprites -> in progress...');
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
                                    fill  : '#606060',
                                    stroke: 'none'
                                }),
                                lockPart = surface.add({
                                    type            : 'rect',
                                    width           : unitWidth / 4,
                                    height          : unitHeight / 4,
                                    x               : rectX - 5,
                                    y               : rectY - 5,
                                    radius          : 36,
                                    fill            : "#000000",
                                    stroke          : "#000",
                                    "stroke-width"  : 1,
                                    "stroke-opacity": 1
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
                                    surface: surface,
                                    zIndex : 2
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
                        CPS.add(drawnUnitSprites);

                        console.log('SpriteCanvasDrawer :: Drawing - > unit sprites -> finished...');
                        return true;
                    },
                    drawHostTiles  : function () {
                        console.log('SpriteCanvasDrawer :: Drawing - > host tiles -> in progress...');
                        var boardSize = board.getSize(),
                            unitsCount = unitStore.getTotalCount(),
                            xCount = Math.floor(boardSize['width'] / sizes['tile']['width']),
                            yCount = Math.floor(boardSize['height'] / sizes['tile']['height']),
                            tile = undefined;

                        if (!Ext.isDefined(board['surface'])) {
                            console.error('SpriteCanvasDrawer :: Surface not ready...');
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
                        console.log('SpriteCanvasDrawer :: Drawing - > host tiles -> finished...');
                        return tilesSprites.length > 0;
                    }
                }
            }
        }),
        drawCanvas = function () {
            var stepResult = SCD.drawHostTiles();
            if (stepResult === true) {
                stepResult = SCD.drawUnitSprites();
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
        switchLockOnUnit = function (unit_id) {
            var spriteRecord = CPS.findRecord('unit_id', unit_id);
            console.log('CanvasProcessor :: Lock will be ', (spriteRecord.get('locked') === true ? 'disabled' : 'enabled'));

            if (spriteRecord.get('locked') === true) {
                SLU.lockOff(spriteRecord);
                spriteRecord.set('locked', false);
            } else {
                SLU.lockOn(spriteRecord);
                spriteRecord.set('locked', true);
            }

            return true;
        },
        privateListeners = {
            boardContextMenu   : function (event, target) {
                console.log('CanvasProcessor :: Lister -> board -> contextmenu -> triggered...\nevent=', event['type'], '\ntarget=', target['id']);
                var sprite_id = target['id'],
                    sprite = CPS.findBySprite(sprite_id);

                event.preventDefault();
                if (!SLU.isLockOn() || SLU.isLocked(sprite)) {
                    me.fireEvent('unitmenu', event['currentTarget'], event.getXY(), sprite.get('unit_id'));
                } else if (!SLU.isLocked(sprite)) {
                    me.fireEvent('unitlockedoff', sprite.get('unit_id'));
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
        };
    return {
        requires   : [
            'WMS.model.sprite.Unit'
        ],
        mixins     : {
            observable: 'Ext.util.Observable'
        },
        constructor: function (config) {
            me = this;

            // init block
            me.mixins.observable.constructor.call(me, config);
            board = config['board'];
            sizes = config['sizes'];
            unitStore = config['unitStore'];
            CPS = Ext.create('CanvasProcessorStorage');
            board.center();
            // init block

            me.addEvents(
                'unitclick',
                'unitmenu',
                'unitrelease',
                'unitlockedoff'
            );

            console.log('CanvasProcessor :: init canvas=', board, ', sizes=', sizes);
            me.callParent(arguments)
        },
        draw       : function (redraw) {
            console.log('CanvasProcessor :: drawing in progress...');
            redraw = (Ext.isDefined(redraw) ? redraw : false);
            if (redraw === true) {
                SDU.clearCanvas();
            }
            var result = drawCanvas();
            console.log('CanvasProcessor :: drawing finished...');
            return result;
        },
        switchLock : function (unit_id) {
            console.log('CanvasProcessor :: switching lock on unit=', unit_id);
            var status;
            {
                status = switchLockOnUnit(unit_id);
            }
            console.log('CanvasProcessor :: switched lock on unit=', unit_id);
            return status;
        }
    }
});