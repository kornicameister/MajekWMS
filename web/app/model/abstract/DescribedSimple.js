/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 01:57
 */

Ext.define('WMS.model.abstract.DescribedSimple', {
    extend: 'WMS.model.abstract.Simple',
    fields: [
        {
            name: 'description',
            type: 'string'
        }
    ]
});