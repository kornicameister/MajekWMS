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
        var me = this;
        me['tiles'] = tiles;

        // @TODO must add support for dragging some shit
    }
});