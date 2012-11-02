/*
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 02.11.12
 * Time   : 15:14
 */

Ext.define('WMS.view.manager.abstract.DetailsHolder', {
    extend     : 'Ext.panel.Panel',
    alias      : 'widget.managerdetailsholder',
    title      : 'Szczegóły',
    iconCls    : 'icon-details',
    autoScroll : true,
    layout     : {
        type : 'hbox',
        pack : 'center',
        align: 'stretch'
    },
    bodyPadding: 5,
    defaults   : {
        bodyPadding: 2,
        frame      : true,
        margin     : 1
    },
    defaultType: 'grid',
    tools      : [
        {
            type   : 'refresh',
            tooltip: 'Odśwież dane'
        }
    ]
});