/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : WMSConfiguratiom
 * Package: store
 * Created: 24-09-2012
 */

Ext.define('WMS.store.InitialStore', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.InitialModel',

    autoLoad: true,
    autoSync: true,

    getWarehouses: function () {
        return this.getAt(0).get('warehouses');
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
    },

    getUnitTypes: function () {
        return this.getAt(0).get('unitTypes');
    }
});