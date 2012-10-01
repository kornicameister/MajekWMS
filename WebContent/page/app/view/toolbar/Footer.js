/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 02:28
 * File   : Footer
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.Footer', {
    itemId     : 'footerToolbar',
    extend     : 'Ext.ux.StatusBar',
    alias      : 'widget.wmsfbar',
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
            text   : 'Save',
            iconCls: 'view-footer-saveButton',
            width  : 100
        },
        {
            itemId : 'refreshButton',
            text   : 'Refresh',
            iconCls: 'view-footer-refreshButton',
            width  : 100
        }
    ],

    // values when cleared
    defaultText: 'No pending operation...'
});

