/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 06.11.12
 * Time   : 16:31
 */

Ext.define('WMS.view.wizard.company.Summary', {
    extend            : 'Ext.panel.Panel',
    alias             : 'widget.companysummary',
    alternateClassName: 'WMS.panel.summary.Company',
    tpl               : new Ext.XTemplate(
        '<div class="companySummary">',
        '<div class="company">',
        '<tpl for=".">',
        '<p>Skrót: <b>{name}</b></p>',
        '<p>Firma: <b>{longname}</b></p>',
        '</tpl>',
        '</div>',
        '<div class="warehouse">',
        '<tpl for="warehouse">',
        '<p>Telefon: <b>{phone}</b></p>',
        '<p>Fax: <b>{fax}</b></p>',
        '<p>NIP: <b>{nip}</b></p>',
        '<p>Numer konta: <b>{account}</b></p>',
        '</tpl>',
        '</div>',
        '</div>'
    )
});