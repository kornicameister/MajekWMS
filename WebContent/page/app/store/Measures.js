/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 19.10.12
 * Time   : 10:37
 */

/**
 * @class WMS.store.Measures
 * @description This class represent store unit configured
 * to operate directly on Measure
 */
Ext.define('WMS.store.Measures', {
    extend: 'Ext.data.Store',
    model : 'WMS.model.entity.Measure',

    autoLoad: true,
    autoSync: false
});