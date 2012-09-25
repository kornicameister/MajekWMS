/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : WMSConfiguratiom
 * Package: store
 * Created: 24-09-2012
 */

Ext.define('WMS.store.WMSConfiguration', {
    extend: 'Ext.data.Store',
    
    autoLoad: true,
    model : 'WMS.model.WMSConfiguration'
    	
}, function () {
    console.log('WMS.store.WMSConfiguration successfully defined');
});