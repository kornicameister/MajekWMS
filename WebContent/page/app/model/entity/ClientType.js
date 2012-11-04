/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 01:32
 */

Ext.define('WMS.model.entity.ClientType', {
    extend      : 'Ext.data.Model',
    fields      : [
        'id', 'name'
    ],
    associations: [
        {name: 'client', type: 'belongsTo', model: 'WMS.model.entity.Client'}
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/clienttype'
    }
});