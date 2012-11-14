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
        '<div class="wizard-company-summary">',
        '<div class="company">',
            '<p>Skrót: <strong>{name}</strong></p>',
            '<p>Firma: <strong>{longName}</strong></p>',
        '</div>',
            '<div class="warehouse">',
                '<tpl for="warehouse">',
                    '<p>Magazyn: <strong>{name}</strong></p>',
                    '<p class="description">Opis: <strong>{description}</strong></p>',
                    '<p>Utworzony: <strong>{createdDate}</strong></p>',
                    '<p>Rozmiar: <strong>{size}</strong></p>',
                '</tpl>',
            '</div>',
        '</div>'
    )
});
