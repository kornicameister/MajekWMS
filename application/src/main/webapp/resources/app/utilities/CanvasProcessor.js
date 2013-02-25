/**
 * Created with IntelliJ IDEA.
 * User: kornicameister
 * Date: 20.02.13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */

Ext.define('WMS.utilities.CanvasProcessor', function () {
    var me = this,
        cachedTileId = undefined,
    //MODULES
        /**
         * SpriteLockingUtility provides functionality most recognizable in
         * the context of UI. By calling method defined within it, whole
         * surface along with defined sprites may be affected and the in the
         * end user is presented with some custom outlook and knows which
         * sprite is currently being affected.
         */
            _1_SLU = Ext.define(null, function (SpriteLockingUtility) {
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
                            var group = UNITS.get(spriteRecord.getId()),
                                lock = group.get(2);
                            lock.animate(_spriteAnimationsConfig.lockAnim);
                        }
                    },
                    disableSpritesExceptFor: function (spriteRecord) {
                        var nonSelected = UNITS.filter(new Ext.util.Filter({
                            filterFn: function (item) {
                                return item['id'] !== spriteRecord.getId()
                            }
                        }), spriteRecord.getId());
                        nonSelected.eachKey(function (spriteId, sprite) {
                            sprite.animate(_spriteAnimationsConfig.disabledSpriteAnim);
                        });
                    },
                    resetSpritesToBasicView: function () {
                        UNITS.eachKey(function (spriteId, sprite) {
                            sprite.animate(_spriteAnimationsConfig.enabledSpriteAnim);
                            sprite.getAt(2).animate(_spriteAnimationsConfig.unlockAnim);
                        });
                    }
                };
            return {
                statics: {
                    /**
                     * Quite useful method that I use to programitically
                     * handle the situation where one of the dozen
                     * of components need to be locked off, which means
                     * that it can be easily manipulated across canvas.
                     *
                     * This method recognizes what do, upon current situtation
                     *
                     * @param unit_id
                     * @return {boolean}
                     */
                    handleLockOnOff    : function (unit_id) {
                        var spriteRecord = _2_CPS.findRecord('unit_id', unit_id);
                        console.log('CanvasProcessor :: Lock will be ', (spriteRecord.get('locked') === true ? 'disabled' : 'enabled'));

                        if (spriteRecord.get('locked') === true) {
                            _1_SLU.lockOff(spriteRecord);
                            spriteRecord.set('locked', false);
                        } else {
                            _1_SLU.lockOn(spriteRecord);

                            var ussRecord = _5_USS.findRecord('tileId', (function () {
                                return TILES.indexOfKey(spriteRecord.get('tile_id'));
                            }()));
                            ussRecord.set('tileId', TILES.indexOfKey(cachedTileId));

                            spriteRecord.set('locked', true);
                        }

                        return true;
                    },
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
                    isLockOff          : function () {
                        return Ext.isDefined(_unlockedSpriteId);
                    },
                    isNotLocked        : function (sprite) {
                        var isDefined = _1_SLU.isLockOff();
                        if (isDefined === true) {
                            isDefined = sprite.getId() === _unlockedSpriteId;
                        }
                        return isDefined;
                    }
                }
            }
        }),
        /**
         * Internal storage of the WMS.utilities.CanvasProcessor. By having its type
         * determined as memory, it is refreshed with every page refreshing. Holds
         * all kinds of runtime-like metadata that are useful when establishing
         * the information about current sprite location.
         */
            _2_CPS = Ext.define('CanvasProcessorStorage', {
            extend      : 'Ext.data.Store',
            fields      : [
                'id',
                'rect_id',
                'text_id',
                'unit_id',
                'lock_id',
                'tile_id',
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
                } else if ((matchedSprite = self.findByLock(sprite_id)) != null) {
                    return matchedSprite;
                } else if ((matchedSprite = self.findByTile(sprite_id)) != null) {
                    return matchedSprite;
                } else {
                    return undefined;
                }
            },
            findByTile  : function (tile) {
                var self = this,
                    record = undefined;
                if (tile.indexOf('ext-') >= 0) {
                    record = self.findRecord('tile_id', tile);
                } else {
                    record = self.findRecord('tile_id', tile['id']);
                }
                return record;
            },
            findByLock  : function (lock) {
                var self = this,
                    record = undefined;
                if (lock.indexOf('ext-') >= 0) {
                    record = self.findRecord('lock_id', lock);
                } else {
                    record = self.findRecord('lock_id', lock['id']);
                }
                return record;
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
        /**
         * Canvas drawer closes up the functionality responsible for drawing/redrawing the surface.
         * It can either remove all the content and than ask the server for updated, draw all
         * shapes in the result.
         */
            _3_SCD = Ext.define(null, function (SpriteCanvasDrawer) {
            var locateTile = function (it, unit_id, fromServer) {
                var tile = TILES.getAt(it);
                if (fromServer) {
                    var tileIndex = _5_USS.findRecord('unit_id', unit_id).get('tileId');
                    tile = TILES.getAt(tileIndex);
                }
                return tile;
            };
            return {
                statics: {
                    clearCanvas    : function () {
                        board['surface'].removeAll(true);
                        TILES.clear();
                        UNITS.clear();
                        _2_CPS.removeAll();
                        _5_USS.removeAll();
                    },
                    drawUnitSprites: function (fromServer) {
                        //@TODO add reading cached drawing from database
                        console.log('SpriteCanvasDrawer :: Drawing - > unit sprites -> in progress...');
                        var self = me,
                            drawnUnitSprites = [],
                            toBePersistedMetaSprites = [],
                            surface = board['surface'],
                            unitWidth = sizes['unit']['width'],
                            unitHeight = sizes['unit']['height'],
                            it = 0;

                        unitStore.each(function (unitRecord) {
                            console.log(Ext.String.format('CanvasProcessor :: Drawing - > unit sprites -> rect for [ {0}/{1} ] unit',
                                unitRecord.getId(),
                                unitRecord.get('name')));

                            var index = drawnUnitSprites.length,
                                selectedTile = locateTile(index, unitRecord.getId(), fromServer),
                                unitName = unitRecord.get('name'),
                                unitSize = unitRecord.get('size'),
                                unitType = unitRecord.getType().get('name'),
                                tileBBox = selectedTile.getBBox(),
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
                                    type            : 'circle',
                                    x               : rectX,
                                    y               : rectY,
                                    radius          : unitWidth / 8,
                                    fill            : "#000000",
                                    stroke          : "#000",
                                    "stroke-width"  : 1,
                                    "stroke-opacity": 1
                                }),
                            // 2. create text shape
                                textPart = surface.add({
                                    type  : 'text',
                                    text  : unitName + '\n[' + unitType + ']' + '\n[' + unitSize + ']',
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

                            // 5. push them to array, fields ['id', 'rect_id', 'text_id', 'unit', 'marked']
                            drawnUnitSprites.push({
                                id     : unitSprite['id'],
                                rect_id: rectPart['id'],
                                text_id: textPart['id'],
                                lock_id: lockPart['id'],
                                tile_id: selectedTile['id'],
                                unit_id: unitRecord.getId()
                            });
                            if (!fromServer) {
                                toBePersistedMetaSprites.push({
                                    tileId : it++,
                                    unit_id: unitRecord.getId()
                                });
                            }
                            UNITS.add(unitSprite['id'], unitSprite);

                            // 6. show them
                            unitSprite.setAttributes({
                                opacity: 0.8
                            });
                            unitSprite.show(true);
                        }, self);

                        // 7. save them to local storage
                        _2_CPS.add(drawnUnitSprites);

                        // 8. persist them
                        _5_USS.add(toBePersistedMetaSprites);

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
                                TILES.add(tile['id'], tile);
                            }
                        }
                        console.log('SpriteCanvasDrawer :: Drawing - > host tiles -> finished...');
                        return TILES.getCount() > 0;
                    }
                }
            }
        }),
        /**
         * SpriteMovingModule has some useful methods that are to handle sprites' reallocating at the surface.
         * If, for instance, whole sprite (group) is initially located on the tile at x=1,y=1, that this
         * module will be able to grap this group and place on the tile at x=4,y=20
         */
            _4_SMM = Ext.define(null, function () {
            return {
                statics: {
                    moveUnitToTile: function (unit_id, tile_id) {
                        var tile = TILES.get(tile_id),
                            unitRecord = _2_CPS.findRecord('unit_id', unit_id),
                            originTile = TILES.get(unitRecord.get('tile_id')),
                            unit = UNITS.get(unitRecord.getId());

                        if (Ext.isDefined(tile) && Ext.isDefined(unit)) {

                            var tileBBox = tile.getBBox(),
                                unitWidth = sizes['unit']['width'],
                                rectX = Math.floor(tileBBox['x'] + ((tileBBox['width'] - unitWidth) / 2)),
                                rectY = Math.floor(tileBBox['y'] + ((tileBBox['height'] - unitWidth) / 2));

                            function stepOne() {
                                originTile.animate({
                                    duration: 1000,
                                    to      : {
                                        fill: '#009900'
                                    }
                                });
                                tile.animate({
                                    duration: 1000,
                                    to      : {
                                        fill: '#009900'
                                    }
                                });
                                stepTwo();
                            }

                            function stepTwo() {
                                unit.animate({
                                    keyframes: {
                                        50 : {
                                            x: rectX / 2,
                                            y: rectY / 2
                                        },
                                        100: {
                                            x: rectX,
                                            y: rectY
                                        }
                                    },
                                    duration : 1000,
                                    listeners: {
                                        'afteranimate': stepThree
                                    }
                                });
                            }

                            function stepThree() {
                                tile.animate({
                                    to      : {
                                        fill: '#C0C0C0'
                                    },
                                    duration: 1000
                                });
                                originTile.animate({
                                    to      : {
                                        fill: '#C0C0C0'
                                    },
                                    duration: 1000
                                });
                                stepFour();
                            }

                            function stepFour() {
                                cachedTileId = tile_id;
                            }

                            stepOne();
                        }
                    }
                }
            }
        }),
        /**
         * Placeholder for the server that interacts between client and the server directly.
         */
            _5_USS = undefined,
    //MODULES
        TILES = new Ext.util.MixedCollection(),
        UNITS = new Ext.util.MixedCollection(),
        board = undefined,
        sizes = undefined,
        unitStore = undefined,
        clearCanvas = function () {
            _3_SCD.clearCanvas();
        },
        drawCanvas = function () {
            _5_USS.load({
                scope   : me,
                callback: function (records) {
                    console.log('CanvasProcessor :: unit sprites persistence :: records=', records);
                    _3_SCD.drawHostTiles();
                    _3_SCD.drawUnitSprites(records.length !== 0);
                    setOtherListeners();
                }
            })
            return true;
        },
        setOtherListeners = function () {
            var surface = board['surface'];

            if (!Ext.isDefined(surface)) {
                console.error('CanvasProcessor :: Global listeners -> setting failed, no surface...');
                return false;
            }

            me.mon(board.getEl(), 'contextmenu', privateListeners['surfaceContextMenu'], me);

            return true;
        },
        privateListeners = {
            surfaceContextMenu: function (event, target) {
                var sprite_id = target['id'],
                    sprite = _2_CPS.findBySprite(sprite_id);

                event.preventDefault();
                try {
                    if (Ext.isDefined(sprite)) {
                        console.log('CanvasProcessor :: contextMenu :: associated shape',
                            '\nevent=', event['type'],
                            '\ntarget=', target['id']);

                        if (!_1_SLU.isLockOff() || _1_SLU.isNotLocked(sprite)) {
                            console.log('CanvasProcessor :: contextMenu :: shape :: unit');
                            me.fireEvent('unitmenu', event['currentTarget'], event.getXY(), sprite.get('unit_id'));
                        } else if (!_1_SLU.isNotLocked(sprite)) {
                            console.log('CanvasProcessor :: contextMenu :: shape :: unit :: locked off');
                            me.fireEvent('unitlockedoff', sprite.get('unit_id'));
                        }
                    } else {
                        console.log('CanvasProcessor :: contextMenu :: unassociated shape',
                            '\nevent=', event['type'],
                            '\ntarget=', target['id']);

                        me.fireEvent('tilemenu', event['currentTarget'], event.getXY(), sprite_id);
                    }
                } catch (exception) {
                    console.log('CanvasProcessor :: contextMenu :: exception - ', exception);
                }
            }
        };
    return {
        requires   : [
            'WMS.store.UnitSprites'
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
            _2_CPS = Ext.create('CanvasProcessorStorage');
            _5_USS = Ext.create('WMS.store.UnitSprites');
            board.center();
            // init block

            me.addEvents(
                'unitclick',
                'unitmenu',
                /**
                 * @event tileMenu This event is fired if context menu request
                 * is detected upon hosting tile or any other object
                 * different than unit sprite
                 * @param currentTarget coming from event object
                 * @param xy  where event occurred
                 * @param tile_id
                 */
                'tilemenu',
                'unitrelease',
                'unitlockedoff'
            );

            console.log('CanvasProcessor :: init canvas=', board, ', sizes=', sizes);
            me.callParent(arguments);
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
        },
        switchLock : function (unit_id) {
            console.log('CanvasProcessor :: switching lock on unit=', unit_id);
            var status;
            {
                status = _1_SLU.handleLockOnOff(unit_id);
            }
            if (status) {
                console.log('CanvasProcessor :: switched lock on unit=', unit_id);
            }
            return status;
        },
        moveToTile : function (unit_id, tile_id) {
            console.log('CanvasProcessor :: moving unit=', unit_id, ' to tile=', tile_id);

            var status;
            {
                status = _4_SMM.moveUnitToTile(unit_id, tile_id);
            }
            if (status) {
                console.log('CanvasProcessor :: moved unit=', unit_id, ' to tile=', tile_id);
            }
            return status;
        }
    }
});