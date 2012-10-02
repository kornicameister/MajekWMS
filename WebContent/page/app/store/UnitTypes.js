/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 01.10.12
 * Time   : 15:23
 */

/**
 * @class WMS.store.UnitTypes
 * @description This class represent store unit configured
 * to operate directly on UnitTypes
 */
Ext.define('WMS.store.UnitTypes', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.UnitType',

    autoLoad: false,
    autoSync: true
});