/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 09:44
 */

Ext.define('WMS.model.sprite.Unit', {
    extend      : 'Ext.data.Model',
    fields      : [
        {
            name: 'tileId',
            type: 'int'
        },
        {
            name   : 'unit_id',
            type   : 'int',
            mapping: 'unit.id'
        }
    ],
    associations: [
        {
            type          : 'hasOne',
            model         : 'WMS.model.entity.Unit',
            associationKey: 'unit',
            getterName    : 'getUnit',
            setterName    : 'setUnit',
            foreignKey    : 'unit_id'
        }
    ],
    proxy       : {
        type: 'wms',
        url : 'wms/agent/unitsprite'
    }
});