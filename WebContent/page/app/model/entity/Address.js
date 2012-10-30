/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 30.10.12
 * Time   : 14:58
 */

Ext.define('WMS.model.entity.Address', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'id',
            type: 'int'
        },
        {
            name: 'street',
            type: 'string'
        },
        {
            name: 'postcode',
            type: 'string'
        },
        {
            name: 'city_id',
            type: 'string'
        }
    ],

    associations: [
        {
            type          : 'hasOne',
            mode          : 'WMS.model.entity.City',
            associatedName: 'city',
            setterName    : 'setCity',
            getterName    : 'getCity',
            associationKey: 'city_id'
        }
    ]
});