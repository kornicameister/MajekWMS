/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 02.10.12
 * Time   : 08:37
 */

/**
 * @class WMS.store.Invoices
 * @description This class represent store unit configured
 * to operate directly on UnitTypes, but in simplified mode
 * This store is forbid to call CRUD actions except for READ
 */
Ext.define('WMS.store.Invoices', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Invoice',

    autoLoad: true,
    autoSync: true
});