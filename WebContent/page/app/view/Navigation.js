/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 14.09.12
 * Time   : 03:25
 * File   : Navigation
 * Package: view
 * Created: 14-09-2012
 */

Ext.define('WMS.view.Navigation', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.navigation',

    itemId: 'navigator',
    title : 'WMS - Navigator',

    width      : 200,
    margins    : {
        right: 3
    },
    collapsible: true,
    collapsed  : true
});