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

    getWarehouses: function () {
        return this['data'];
    },

    addWarehouse: function (model) {
        var configuration = this.getAt(0);
        model = Ext.apply({
            size: 0
        }, model);

        configuration.beginEdit();
        configuration.set('warehouses', [model]);
        configuration.endEdit(false);

        console.log(model, this);
    }
});