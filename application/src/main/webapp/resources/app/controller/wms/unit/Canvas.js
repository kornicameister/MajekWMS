/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 14.10.12
 * Time  : 22:56
 */

Ext.define('WMS.controller.wms.unit.Canvas', function () {
    var me = this,
        privateListeners = {
            onUnitSelected: function (unit_id) {
                var self = me,
                    unitStore = self.getStore('Units');

                var unit = unitStore.getById(unit_id),
                    controller = self.getController('wms.unit.Inventory');

                unitStore.setActive(unit);
                controller.loadProductsFromUnit(unit);
            }
        };
    return {
        extend            : 'Ext.app.Controller',
        requires          : [
            'WMS.utilities.CanvasProcessor'
        ],
        stores            : [
            'Warehouses'
        ],
        views             : [
            'wms.unit.Canvas'
        ],
        refs              : [
            { ref: 'unitBoard', selector: 'wmsunitcanvas unitsDrawingCmp' }
        ],
        config            : {
            canvasProcessor: undefined,
            contextMenu    : undefined,
            tileContextMenu: undefined,
            cachedUnit     : undefined,
            cachedTileId   : undefined,
            menuPosState   : {
                1: 'Przenieś w inne miejsce',
                2: 'Zakończ operacje'
            }
        },
        init              : function () {
            console.init('WMS.controller.wms.unit.Canvas initializing... ');
            me = this;
            me.control({
                'wmsunitcanvas #unitsDrawingCmp': {
                    boxready: me.onBoxReady
                }
            }, me);
            me.setContextMenu(Ext.create('Ext.menu.Menu', {
                xtype       : 'menu',
                plain       : true,
                margin      : '0 0 10 0',
                animCollapse: true,
                items       : [
                    {
                        itemId  : 1,
                        text    : 'Edycja',
                        disabled: true
                    },
                    {
                        itemId : 2,
                        text   : 'Lista produktów',
                        iconCls: 'view-wms-inventory'
                    },
                    {
                        itemId: 3,
                        text  : me.getMenuPosState()[1]
                    }
                ],
                listeners   : {
                    'click': {
                        scope: me,
                        fn   : me.onContextMenuClick
                    }
                }
            }));

            me.setTileContextMenu(Ext.create('Ext.menu.Menu', {
                xtype       : 'menu',
                plain       : true,
                margin      : '0 0 10 0',
                animCollapse: true,
                items       : [
                    {
                        itemId: 4,
                        text  : 'Przenieś tutaj'
                    },
                    {
                        itemId: 5,
                        text  : 'Anuluj'
                    }
                ],
                listeners   : {
                    'click': {
                        scope: me,
                        fn   : me.onContextMenuClick
                    }
                }
            }));
        },
        onBoxReady        : function (board) {
            console.log('Canvas :: Drawing chart initialized, board=', board);
            var me = this,
                canvasProcessor;

            me.setCanvasProcessor(Ext.create('WMS.utilities.CanvasProcessor', {
                board    : board,
                unitStore: me.getWarehousesStore().getActive().units(),
                sizes    : {
                    tile: {
                        width : 120,
                        height: 120
                    },
                    unit: {
                        width : 90,
                        height: 90
                    }
                }
            }));

            canvasProcessor = me.getCanvasProcessor();
            // listeners
            {
                me.mon(canvasProcessor, 'unitmenu', me.onUnitMenu, me);
                me.mon(canvasProcessor, 'tilemenu', me.onTileMenu, me);
                me.mon(canvasProcessor, 'unitlockedoff', me.onUnitLockedOff, me);
            }
            // listeners

            // drawing
            canvasProcessor.draw();
        },
        onTileMenu        : function (target, xy, tile_id) {
            var me = this,
                contextMenu = me.getTileContextMenu();
            contextMenu.showAt(xy);
            me.setCachedTileId(tile_id);
        },
        onUnitMenu        : function (target, xy, unit_id) {
            var me = this,
                contextMenu = me.getContextMenu();
            contextMenu.showAt(xy);
            me.setCachedUnit(unit_id);
        },
        onContextMenuClick: function (menu, item) {
            var me = this,
                canvasProcessor = me.getCanvasProcessor();
            switch (item['itemId']) {
                case 1:
                    break;
                case 2:
                    privateListeners.onUnitSelected(me.getCachedUnit());
                    break;
                case 3:
                    if (canvasProcessor.switchLock(me.getCachedUnit())) {
                        item.setText((item['text'] === me.getMenuPosState()[1] ? me.getMenuPosState()[2] : me.getMenuPosState()[1]));
                    }
                    break;
                case 4:
                    canvasProcessor.moveToTile(me.getCachedUnit(), me.getCachedTileId());
                    break;
                case 5:
                    break;
            }
            Ext.getCmp('statusBar').setStatus({
                text : Ext.String.format('Akcja na elemencie {0}: ', item['text']),
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        },
        onUnitLockedOff   : function () {
            var msg = 'Musisz zapisać zmiany, aby móc zmieniać położenia innych elementów.';
            Ext.Msg.show({
                title  : 'Akcja niedozwolona',
                msg    : msg,
                buttons: Ext.Msg.OK,
                icon   : Ext.Msg.WARNING
            });
            Ext.getCmp('statusBar').setStatus({
                text : msg,
                clear: {
                    wait       : 10000,
                    anim       : true,
                    useDefaults: false
                }
            });
        }
    }
});
