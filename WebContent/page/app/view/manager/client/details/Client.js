/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 02.11.12
 * Time   : 15:15
 */

Ext.define('WMS.view.manager.client.details.Client', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.clientdetails',
    title : 'Szczegóły odbiorcy',
    tpl   : new Ext.XTemplate(
        '<div class="clientDetails">',
            '<div class="basic">',
                '<p>Odbiorca: <b>{name}</b></p>',
                '<p>Firma: <b>{company}</b></p>',
                '<p>Opis: <b>{description}</b></p>',
            '</div>',
            '<div class="extended">',
                //---- details association ---/
                '<tpl for="details">',
                    '<div class="details">',
                    '<p>Telefon: <b>{phone}</b></p>',
                    '<p>Fax: <b>{fax}</b></p>',
                    '<p>NIP: <b>{nip}</b></p>',
                    '<p>Numer konta: <b>{account}</b></p>',
                '</div>',
            '</tpl>',
            //---- address association ---/
            '<tpl for="address">',
                '<div class="address">',
                    '<p>Ulica: <b>{street}</b></p>',
                    '<p>Kod pocztowy: <b>{postcode}</b></p>',
                    '<tpl for="city">',
                        '<p>Miasto: <b>{name}</b></p>',
                    '</tpl>',
                    '</div>',
                '</tpl>',
            '</div>',
        '</div>'
    )
});