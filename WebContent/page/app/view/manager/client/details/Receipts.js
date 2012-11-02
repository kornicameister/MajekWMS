/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 02.11.12
 * Time   : 15:15
 */

Ext.define('WMS.view.manager.client.details.Receipts', {
    extend : 'Ext.grid.Panel',
    alias  : 'widget.clientreceipts',
    title  : 'Przyjęcia od tego odbiorcy',
    columns: [
        {
            header: 'ID'
        }
    ]
});