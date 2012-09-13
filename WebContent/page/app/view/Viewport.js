/**
 * Project: Majek WMS
 * User   : kornicameister
 * Date   : 13.09.12
 * Time   : 23:38
 * File   : Viewport
 * Created: 13-09-2012
 */

// entry point for file 
(function () {
    Ext.define('WMS.view.Viewport', {
        extend       : 'Ext.Viewport',
        layout       : 'fit',

        initComponent: function () {
            var scope = this;
            Ext.apply(scope, {
                items: {
                    id         : 'viewport',
                    xtype      : 'panel',
                    layout     : 'border',
                    title      : 'WMS Simulator',
                    defaults   : {
                        split : true,
                        border: false
                    }
                }
            });
            scope.callParent(this);
        }
    });
}());
