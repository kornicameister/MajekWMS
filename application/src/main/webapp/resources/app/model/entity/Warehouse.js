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
            foreignKey    : 'warehouse_id'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/warehouse'
    }
});
