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
        'WMS.model.entity.Product',
        'WMS.model.sprite.Unit'
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
            model         : 'WMS.model.entity.Product'
        }
    ],

    sorters: [
        'id', 'name'
    ],
    //TODO validations

    setSprite: function (sprite) {
        var me = this;
        if (!Ext.isDefined(sprite['isModel'])) {
            sprite = Ext.create('WMS.model.sprite.Unit', {
                id    : sprite['id'],
                sprite: sprite,
                unit  : me
            })
        }
        me['sprite'] = sprite;
        return sprite;
    },

    getSprite: function () {
        var me = this;
        return me['sprite'];
    },

    proxy: {
        type: 'wms',
        url : 'wms/agent/unit'
    }
});