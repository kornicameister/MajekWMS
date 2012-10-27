/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Warehouse
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Warehouse', {
    extend  : 'Ext.data.Model',
    requires: [
        'WMS.model.entity.Unit'
    ],

    fields      : [
        { name: 'id', type: 'int', persist: true},
        { name: 'name', type: 'string' },
        { name: 'description', type: 'string'},
        { name: 'usage', type: 'float', defaultValue: 0.0, convert: convertUsage},
        { name: 'size', type: 'int', defaultValue: 0},
        { name: 'createdDate', type: 'date', serialize: serializeDate}
    ],
    associations: [
        {
            type       : 'hasMany',
            foreignKey : 'warehouse_id',
            name       : 'getUnits',
            model      : 'WMS.model.entity.Unit',
            storeConfig: {
                storeId   : 'Units',
                activeUnit: undefined,

                /**
                 * Method returns unit that is set as active. In other
                 * words it is about the unit chosen from units' grid.
                 * @return WMS.model.entity.Unit
                 */
                getActive: function () {
                    return this.activeUnit;
                },

                /**
                 * Method to set new unit as active
                 * @param unit
                 */
                setActive: function (unit) {
                    this.activeUnit = unit;
                },

                /**
                 * @override Ext.data.Store.sync
                 * @description Overridden version of old sync method that is extend for unit's store
                 * to call for sync method for each underlying product's store
                 */
                sync: function () {
                    var me = this,
                        products = undefined;

                    console.log(Ext.String.format('{0} nested sync called', me['storeId']));

                    me.each(function (unit) {
                        if (Ext.isDefined(unit.products)) {
                            products = unit.products();
                            products.sync();
                        }
                    });

                    me.superclass.sync.call(me);
                },

                listeners: {
                    'update': function (store, unit, ops, modifiedFields) {
                        if (ops !== Ext.data.Model.EDIT) {
                            return;
                        }
                        if (modifiedFields !== null && Ext.isDefined(modifiedFields)) {
                            var contains = Ext.Array.contains;

                            if (contains(modifiedFields, 'size')) {
                                var newUnitSize = 0,
                                    unitSize = unit.get('size');

                                unit.products().each(function (prod) {
                                    newUnitSize += prod.get('pallets');
                                });

                                if (newUnitSize < unitSize || newUnitSize === unitSize) {
                                    unit.set('usage', newUnitSize / unitSize);
                                } else {
                                    store.fireEvent('updatefail', store, unit, WMS.model.entity.Unit.SIZE_EXCEEDED);
                                }
                            }
                        }
                    }
                }
            }
        }
    ],

    proxy: {
        type: 'wms',
        url : 'wms/agent/warehouse'
    },

    toSource: function (desc) {
        var me = this,
            property = undefined,
            source = {};

        if (!Ext.isDefined(desc)) {
            desc = [];
            Ext.each(me.getFields(), function (field) {
                property = {
                    field : field.getName(),
                    header: field.getName().toUpperCase()
                };
                desc.push(property);
            });
        }

        Ext.each(desc, function (d) {
            property = me.get(d['field']);
            source[d['header']] = property;
        });
        return source;
    }
});

function serializeDate(value) {
    return Ext.Date.format(new Date(value), 'Y-m-d');
}
function convertUsage(value) {
    return (value * 100).toFixed(2);
}