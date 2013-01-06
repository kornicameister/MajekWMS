/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 02:05
 */

/**
 * @class WMS.store.ClientTypes
 * @description This class represent store unit configured
 * to operate directly on Measure
 */
Ext.define('WMS.store.ClientTypes', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.ClientType',
    autoLoad: true,
    autoSync: false
});