/**
 * Created with IntelliJ IDEA.
 * User: kornicameister
 * Date: 13.11.12
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */

/**
 * This store represents placeholder
 * where units...defined for given warehouse
 * are stored.
 */
Ext.define('WMS.store.Units', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.Unit',
    autoLoad: false,
    autoSync: true
});
