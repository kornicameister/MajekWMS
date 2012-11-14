/**
 * Created with IntelliJ IDEA.
 * User: kornicameister
 * Date: 13.11.12
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */

Ext.define('WMS.store.Warehouses', {
    extend         : 'Ext.data.Store',
    model          : 'WMS.model.entity.Warehouse',
    autoLoad       : false,
    autoSync       : true,
    activeWarehouse: undefined,
    listeners      : {
        'load': function (me, records) {
            me.setActive(records[0]);
        }
    },
    constructor    : function (config) {
        config = Ext.Object.merge({}, config);

        this.addEvents(
            /**
             * @event activechanged
             * @description fires for every active warehouse change
             * @param warehousesStore
             * @param activeWarehouse
             */
            'activechanged'
        );

        this.callParent([config]);
    },
    setActive      : function (wId) {
        var me = this;
        if (Ext.isNumber(wId)) {
            me.activeWarehouse = this.getById(wId);
        } else {
            me.activeWarehouse = wId;
        }
        me.fireEvent('activechanged', me, me.activeWarehouse);
    },
    getActive      : function () {
        return this.activeWarehouse;
    }
});
