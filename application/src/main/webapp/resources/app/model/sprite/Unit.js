/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 18.10.12
 * Time   : 09:44
 */

Ext.define('WMS.model.sprite.Unit', {
    extend: 'Ext.data.Model',
    fields: [
        'id', 'rect_id', 'text_id', 'unit_id', 'lock_id', 'tile_id',
        { name: 'marked', type: 'boolean', defaultValue: false},
        // locked = true, sprite can not be dragged
        // locked = false => unlocked, sprite can be dragged
        { name: 'locked', type: 'boolean', defaultValue: true}
    ],
    proxy : {
        type: 'localstorage',
        id  : 'blablablac'
//        type: 'wms',
//        url : 'wms/data/unit/sprite'
    }
});