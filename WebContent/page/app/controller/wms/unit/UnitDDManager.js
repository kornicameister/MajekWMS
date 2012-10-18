/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 17.10.12
 * Time   : 12:21
 */

Ext.define('WMS.controller.wms.unit.UnitDDManager', {
    extend: 'Ext.app.Controller',

    constructor: function (config) {
        config = Ext.apply({
            tiles: undefined
        }, config);
        Ext.apply(this, config);
        this.callParent(arguments);
    },

    init: function () {
        console.init("WMS.controller.wms.unit.UnitDDManager is initializing...");
    },

    setTiles: function (tiles) {
        console.log('UnitDDManager :: Synchronizing with tiles...');
        var me = this,
            unit = undefined;
        me['tiles'] = tiles;

        // @TODO must add support for dragging some shit

        Ext.each(me['tiles'], function (tile) {
            unit = tile['unit'];
            if (Ext.isDefined(unit)) {
                tile.mon(unit, 'dblclick', me.onUnitSelected, me);
            } else {
                return false;
            }
        });
    },

    onUnitSelected: function (event, target, options) {
        console.log('UnitDDManager :: Unit has been selected, loading products in progress...');
        console.dir(event, target, options);
    }
});