/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 02:28
 * File   : Footer
 * Created: 14-09-2012
 */

Ext.define('WMS.view.toolbar.Footer', {
    extend            : 'Ext.toolbar.Toolbar',
    alias             : 'widget.wmsfooter',
    alternateClassName: 'wms.toolbar.footer',
    defaultType       : 'button',
    defaults          : {
        margin: {
            top   : 1,
            left  : 5,
            right : 0,
            bottom: 1
        }
    },
    items             : [
        {
            itemId : 'statusBar',
            xtype  : 'textfields',
            enabled: 'false',
            text   : 'Application ready...'
        },
        {
            itemId : 'saveButton',
            text   : 'Save',
            iconCls: 'view-toolbar-saveButton'
        },
        {
            itemId : 'refreshButton',
            text   : 'Refresh',
            iconCls: 'view-toolbar-settingsButton'
        }
    ]
});

