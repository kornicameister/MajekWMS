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
    /**
     * This method is responsible for setting active warehouse to be used for working
     * with units and even deeper in hierarchy of associations.
     *
     * <b>Fires the <i>activechanged</it> event </b> as the result of change in the place
     * of active warehouse.
     *
     * @param wId
     * @param callback, method to be called as the result of successful setting active warehouse
     * @return currently active warehouse or undefined
     */
    setActive      : function (wId, callback) {
        var me = this,
            success = false;

        if (Ext.isNumber(wId)) {
            me.activeWarehouse = this.getById(wId);
        } else {
            me.activeWarehouse = wId;
        }

        if (Ext.isDefined(me.activeWarehouse)) {
            me.fireEvent('activechanged', me, me.activeWarehouse);
            success = true;
        }

        if (Ext.isDefined(callback)) {
            callback.apply(callback, [success]);
        }

        return me.activeWarehouse;
    },
    getActive      : function () {
        return this.activeWarehouse;
    }
});
