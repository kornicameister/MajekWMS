/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 10:48
 */

Ext.define('WMS.view.manager.abstract.ClientGrid', {
    extend : 'WMS.view.manager.abstract.Grid',
    uses   : [
        'WMS.view.manager.abstract.Grid'
    ],
    alias  : 'widget.clientgrid',
    columns: [
        {
            header   : 'Skrót',
            dataIndex: 'name',
            width    : 160
        },
        {
            header   : 'Firma',
            dataIndex: 'company',
            width    : 260
        },
        {
            header   : 'Szczegóły',
            dataIndex: 'description',
            flex     : 3
        }
    ]
});