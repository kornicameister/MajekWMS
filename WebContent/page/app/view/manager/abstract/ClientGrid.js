/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 04.11.12
 * Time   : 10:48
 */

Ext.define('WMS.view.manager.abstract.ClientGrid', {
    extend            : 'Ext.grid.Panel',
    alternateClassName: 'WMS.grid.Clients',
    alias             : 'widget.clientgrid',
    autoScroll        : true,
    viewConfig        : {
        emptyText: 'Nie zdefiniowano jeszcze żadnego odbiorcy...'
    },
    columns           : [
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