/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 17:12
 */

Ext.define('WMS.model.entity.City', {
    extend      : 'WMS.model.abstract.Simple',
    associations: [
        {
            name : 'address',
            type : 'belongsTo',
            model: 'WMS.model.entity.Address'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/city'
    }
});