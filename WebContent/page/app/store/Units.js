/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 03.10.12
 * Time   : 13:03
 */

Ext.define('WMS.store.Units',{
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Unit',

    autoLoad: true,
    autoSync: true,

    addUnit: function (model) {
        var unit = Ext.create('WMS.model.entity.Unit',model),
            lastWarehouse = Ext.getStore('Warehouses').getActive();

        unit.setType(model['type']);
        lastWarehouse.units().add(unit);

        unit.save();
    }
});