/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:47
 */

Ext.define('WMS.store.Companies', {
    extend  : 'Ext.data.Store',
    model   : 'WMS.model.entity.Company',
    autoLoad: false,
    autoSync: true,
    add     : function (rawCompany) {
        var me = this,
            warehouse = rawCompany['warehouse'],
            company = rawCompany;

        company = Ext.create('WMS.model.entity.Company', company);
        warehouse = Ext.create('WMS.model.entity.Warehouse', warehouse);
        company['warehouse'] = warehouse;

        me.callParent([company]);
    }
});
