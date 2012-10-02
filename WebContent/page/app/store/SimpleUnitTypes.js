/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 01.10.12
 * Time   : 15:23
 */

/**
 * @class WMS.store.SimpleUnitTypes
 * @description This class represent store unit configured
 * to operate directly on UnitTypes, but in simplified mode
 * This store is forbid to call CRUD actions except for READ
 */
Ext.define('WMS.store.SimpleUnitTypes', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.UnitTypeSimple',

    autoLoad: true,
    autoSync: false
});