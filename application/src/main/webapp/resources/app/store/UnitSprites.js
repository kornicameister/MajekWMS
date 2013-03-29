/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 21.10.12
 * Time   : 23:11
 */

Ext.define('WMS.store.UnitSprites', {
    extend  : 'Ext.data.Store',
    requires: [
        'WMS.model.sprite.Unit'
    ],
    model   : 'WMS.model.sprite.Unit',
    sorters : [
        {
            property : 'tileId',
            direction: 'DESC'
        }
    ],
    autoSync: true
});