/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 02.11.12
 * Time   : 15:15
 */

Ext.define('WMS.view.manager.client.details.Releases', {
    extend : 'Ext.grid.Panel',
    alias  : 'widget.clientreleases',
    title  : 'Wydania dla tego odbiorcy',
    columns: [
        {
            header: 'ID'
        }
    ]
});