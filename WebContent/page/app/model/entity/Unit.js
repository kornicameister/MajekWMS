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

    fields: [
        { name: 'id', type: 'int', persist: true},
        { name: 'name', type: 'string'},
        { name: 'description', type: 'string'},
        { name: 'warehouse_id', type: 'int', mapping: 'warehouse.id'},
        { name: 'unittype_id', type: 'int', mapping: 'type.id'},
        { name: 'size', type: 'int', defaultValue: 0, persist: true},
        { name: 'maximumSize', type: 'int', defaultValue: 0}
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
            foreignKey    : 'unit_id'
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
            products = me.products(),
            productRecord = products.add(product);

        return productRecord;
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