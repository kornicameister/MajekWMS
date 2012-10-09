/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : WMSConfiguratiom
 * Package: store
 * Created: 24-09-2012
 */

/**
 * @class WMS.store.Warehouses
 * @description Store to hold next records of WMS.model.entity.Warehouse type
 */
Ext.define('WMS.store.Warehouses', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Warehouse',

    autoLoad: true,
    autoSync: true,

    activeWarehouse: undefined,

    constructor: function (config) {
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

        this.callParent([config])
    },

    getWarehouses: function () {
        return this['data'];
    },

    setActive: function (wId) {
        if (Ext.isNumber(wId)) {
            this.activeWarehouse = this.getById(wId);
        } else {
            this.activeWarehouse = wId;
        }
        this.fireEvent('activechanged', this, this.activeWarehouse);
    },

    getActive: function () {
        return this.activeWarehouse;
    },

    addWarehouse: function (model) {
        model = Ext.apply({
            size: 0
        }, model);
        return this.add([model]);
    }
});