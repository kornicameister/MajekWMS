/**
 * Projet: MajekWMS
 * User  : kornicameister
 * Date  : 14.10.12
 * Time  : 22:56
 */

Ext.define('WMS.controller.wms.unit.Canvas', {
    extend        : 'Ext.app.Controller',
    requires      : [
        'WMS.utilities.CanvasProcessor'
    ],
    stores        : [
        'Warehouses'
    ],
    views         : [
        'wms.unit.Canvas'
    ],
    refs          : [
        { ref: 'unitBoard', selector: 'wmsunitcanvas unitsDrawingCmp' }
    ],
    config        : {
        canvasProcessor: undefined
    },
    init          : function () {
        console.init('WMS.controller.wms.unit.Canvas initializing... ');
        var me = this;
        me.control({
            'wmsunitcanvas #unitsDrawingCmp': {
                boxready: me.onBoxReady
            }
        }, me);
    },
    onBoxReady    : function (board) {
        console.log('Canvas :: Drawing chart initialized, board=', board);
        var me = this;

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
        me.getCanvasProcessor().draw();
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
