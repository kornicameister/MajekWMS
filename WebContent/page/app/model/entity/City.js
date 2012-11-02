/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 17:12
 */

Ext.define('WMS.model.entity.City', {
    extend      : 'Ext.data.Model',
    fields      : [
        'id', 'name'
    ],
    associations: [
        {
            name : 'address',
            type : 'belongsTo',
            model: 'WMS.model.entity.Address'
        }
    ],
    sorters     : [
        {
            property: 'name'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/city'
    }
});