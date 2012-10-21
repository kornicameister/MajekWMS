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
        {
            name   : 'id',
            type   : 'int',
            persist: true
        },
        'name',
        'description',
        { name: 'maximumSize', type: 'int', defaultValue: 0},
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
                getActive : function () {
                    return this.activeUnit;
                },
                setActive : function (unit) {
                    this.activeUnit = unit;
                },
                sync      : function () {
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
                }
            }
        }
    ],

    proxy: {
        type: 'wms',
        url : 'wms/agent/warehouse'
    }
});

function serializeDate(value) {
    return Ext.Date.format(new Date(value), 'Y-m-d');
}