/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 30.09.12
 * Time   : 21:45
 */

Ext.define('WMS.model.entity.UnitTypeSimple', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'int', persist: true, mapping: 'idUnitType'},
        { name: 'name', type: 'string', persist: true},
        { name: 'abbreviation', type: 'string', persist: true}
    ]
});