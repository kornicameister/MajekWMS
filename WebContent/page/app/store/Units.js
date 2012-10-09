/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 03.10.12
 * Time   : 13:03
 */

Ext.define('WMS.store.Units', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Unit',

    autoLoad: false,
    autoSync: false,

    addUnit: function (model) {
        var unit = Ext.create('WMS.model.entity.Unit', model),
            lastWarehouse = Ext.getStore('Warehouses').getActive();

        unit.setType(model['type']);
        lastWarehouse.units().add(unit);

        unit.save();
    },

    listeners: {
        write: function (store, operation) {
            var record = operation.getRecords()[0],
                name = Ext.String.capitalize(operation.action),
                verb;

            if (name == 'Destroy') {
                record = operation.records[0];
                verb = 'Destroyed';
            } else {
                verb = name + 'd';
            }
            console.log(name, Ext.String.format("{0} user: {1}", verb, record.getId()));
        }
    }
});