Ext.define('WMS.store.Products', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.Product',
    autoLoad: false,
    autoSync: true
});
