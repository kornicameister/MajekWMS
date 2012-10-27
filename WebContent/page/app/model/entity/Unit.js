/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Unit
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Unit', {
    extend: 'Ext.data.Model',

    requires: [
        'WMS.model.entity.Product'
    ],

    statics: {
        SIZE_EXCEEDED: 1
    },

    fields: [
        { name: 'id', type: 'int', persist: true},
        { name: 'name', type: 'string'},
        { name: 'description', type: 'string'},
        { name: 'warehouse_id', type: 'int', mapping: 'warehouse.id'},
        { name: 'unittype_id', type: 'int', mapping: 'type.id'},
        { name: 'size', type: 'int', defaultValue: 0},
        { name: 'usage', type: 'float', persist: false}
    ],

    associations: [
        {
            type          : 'belongsTo',
            associationKey: 'warehouse',
            model         : 'WMS.model.entity.Warehouse',
            getterName    : 'getWarehouse',
            setterName    : 'setWarehouse',
            foreignKey    : 'warehouse_id'
        },
        {
            type          : 'hasOne',
            associationKey: 'type',
            model         : 'WMS.model.entity.UnitType',
            getterName    : 'getType',
            setterName    : 'setType',
            foreignKey    : 'unittype_id'
        },
        {
            type          : 'hasMany',
            associatedName: 'products',
            model         : 'WMS.model.entity.Product',
            foreignKey    : 'unit_id',
            storeConfig   : {
                constructor: function (config) {
                    // Clone the config so we don't modify the original config object
                    config = Ext.Object.merge({}, config);
                    var me = this;

                    me.addEvents(
                        /**
                         * @event updatefail
                         * @description Fires after update of record has failed
                         * @param store
                         * @param record
                         * @param reason
                         */
                        'updatefail'
                    );
                },
                listeners  : {
                    'update': function (store, record, ops, modifiedFields) {
                        if (modifiedFields !== null && Ext.isDefined(modifiedFields)) {
                            var contains = Ext.Array.contains;

                            if (contains(modifiedFields, 'pallets')) {
                                var unit = Ext.getStore('Units').getById(record.get('unit_id')),
                                    newUnitSize = 0,
                                    unitSize = unit.get('size');

                                store.each(function (prod) {
                                    newUnitSize += prod.get('pallets');
                                });

                                if (newUnitSize < unitSize || newUnitSize === unitSize) {
                                    unit.set('usage', newUnitSize / unitSize);
                                } else {
                                    store.fireEvent('updatefail', store, record, WMS.model.entity.Unit.SIZE_EXCEEDED);
                                }
                            }
                        }
                    }
                }
            }
        }
    ],

    sorters: [
        'id', 'name'
    ],

    proxy: {
        type: 'wms',
        url : 'wms/agent/unit'
    },

    /**
     * Use this method to add new product to unit.
     * @param product
     * @return {*}
     */
    addProduct: function (product) {
        var me = this,
            products = me.products();

        Ext.each(product, function (p) {
            p.set('unit_id', me.getId());
        });

        return products.add(product);
    },

    deleteProduct: function (product) {
        var me = this,
            products = me.products();

        function validateRemove(units, removedRecords) {
            me.mun(products, 'bulkremove', validateRemove);
            return removedRecords;
        }

        me.mon(products, 'bulkremove', validateRemove);
        products.remove(product);
    },

    getProductsCount: function () {
        var me = this;

        return me.products().getCount();
    }
});