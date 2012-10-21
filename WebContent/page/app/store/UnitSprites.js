/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 21.10.12
 * Time   : 23:11
 */

Ext.define('WMS.store.UnitSprites', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.sprite.Unit',

    autoLoad: true,
    autoSync: true
});