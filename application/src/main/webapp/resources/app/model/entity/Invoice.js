/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 24.09.12
 * Time   : 22:39
 * File   : Invoice
 * Package: model.entity
 * Created: 24-09-2012
 */

Ext.define('WMS.model.entity.Invoice', function () {
    var dateFormat = 'Y-n-j',
        daysConverter = function (v, record) {
            var sDate = record.get('createdDate'),
                eDate = record.get('dueDate'),
                days = Ext.Date.getDayOfYear(eDate) -
                    Ext.Date.getDayOfYear(sDate);
            if (days < 0) {
                // different years
                days += (Ext.Date.isLeapYear(sDate) ? 356 : 355)
                    + parseInt(Ext.Date.format(eDate, dateFormat).split('-')[2]);
            }

            return days;
        };
    return {
        extend      : 'Ext.data.Model',
        requires    : [
            'WMS.model.entity.InvoiceType',
            'WMS.model.entity.Client'
        ],
        fields      : [
            'id',
            'invoiceNumber',
            'description',
            {
                name   : 'timeToRealize',
                persist: false,
                type   : 'int',
                convert: daysConverter
            },
            { name: 'client_id', type: 'int', mapping: 'client.id'},
            { name: 'type_id', type: 'int', mapping: 'type.id'},
            { name: 'createdDate', type: 'date', dateFormat: dateFormat},
            { name: 'dueDate', type: 'date', dateFormat: dateFormat}
        ],
        associations: [
            {
                type          : 'hasOne',
                model         : 'WMS.model.entity.InvoiceType',
                associationKey: 'type',
                getterName    : 'getType',
                setterName    : 'setType',
                foreignKey    : 'type_id'
            },
            {
                type          : 'hasOne',
                model         : 'WMS.model.entity.Client',
                associationKey: 'client',
                setterName    : 'setClient',
                getterName    : 'getClient',
                foreignKey    : 'client_id'
            }
        ],
        validations : [
            { name: 'length', field: 'invoiceNumber', min: 1, max: 18},
            { name: 'length', field: 'description', min: 1, max: 250}
        ],
        proxy       : {
            type: 'wms',
            url : 'wms/agent/invoice'
        }
    }
});