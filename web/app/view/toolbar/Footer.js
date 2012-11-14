/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 02:28
 * File   : Footer
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.Footer', {
    id         : 'statusBar',
    itemId     : 'footerToolbar',
    extend     : 'Ext.ux.StatusBar',
    alias      : 'widget.footbar',
    defaultType: 'button',
    defaults   : {
        margin   : {
            top   : 1,
            left  : 5,
            right : 0,
            bottom: 1
        },
        iconAlign: 'left',
        scale    : 'large'
    },
    items      : [
        {
            itemId : 'saveButton',
            text   : 'Zapisz',
            iconCls: 'view-footer-saveButton',
            width  : 100
        },
        {
            itemId : 'refreshButton',
            text   : 'Odśwież',
            iconCls: 'view-footer-refreshButton',
            width  : 100
        }
    ],
    defaultText: 'Brak oczekujących operacji...'
});

