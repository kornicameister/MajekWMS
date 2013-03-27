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
    extend      : 'WMS.model.abstract.DescribedSimple',
    requires    : [
        'WMS.model.entity.Unit'
    ],
    fields      : [
        { name     : 'usage', type: 'float', defaultValue: 0.0,
            convert: function (value) {
                return (value * 100).toFixed(2);
            }
        },
        { name: 'size', type: 'int', defaultValue: 0},
        { name       : 'createdDate', type: 'date',
            serialize: function (value) {
                return Ext.Date.format(new Date(value), 'Y-m-d');
            }
        }
    ],
    associations: [
        {
            type          : 'hasMany',
            model         : 'WMS.model.entity.Unit',
            associatedName: 'units',
            foreignKey    : 'warehouse_id',
            storeConfig   : {
                storeId    : 'Units',
                groupField : 'unittype_id',
                autoLoad   : true,
                autoSync   : true,
                activeUnit : undefined,
                /**
                 * Method returns unit that is set as active. In other
                 * words it is about the unit chosen from units' grid.
                 * @return WMS.model.entity.Unit
                 */
                getActive  : function () {
                    return this.activeUnit;
                },
                /**
                 * Method to set new unit as active
                 * @param unit
                 */
                setActive  : function (unit) {
                    this.activeUnit = unit;
                },
                getProducts: function (unit_id) {
                    var me = this,
                        products = [];

                    if (!Ext.isDefined(unit_id)) {
                        return undefined;
                    } else if (Ext.isDefined(unit_id['isModel'])) {
                        unit_id = unit_id.getId();
                    }

                    return me.getById(unit_id).products();
                },
                listeners  : {
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
    proxy       : {
        type: 'wms',
        url : 'wms/agent/warehouse'
    },
    toSource    : function (desc) {
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
