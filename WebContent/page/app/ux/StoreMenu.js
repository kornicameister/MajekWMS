/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 22.10.12
 * Time   : 12:15
 */

Ext.define('Ext.ux.menu.StoreMenu', {
    extend            : 'Ext.menu.Menu',
    alternateClassName: 'Ext.menu.StoreMenu',
    alias             : 'widget.storemenu',
    requires          : [
        'Ext.data.Store'
    ],

    /**
     * @cfg {Ext.data.Store/Object/String} either existing object of store
     * or literal or string with storeId.
     */
    store: undefined,

    /**
     * @cfg {Boolean} will be used to detect whether or not store should
     * reload before each menu expand
     */
    autoReload: false,

    /**
     * @cfg {String} defines path which is used by StoreMenu to find text
     * that will be displayed within menu
     */
    itemRef: 'name',

    initComponent: function () {
        var me = this;

        if (Ext.isDefined(me.store)) {
            me.store = Ext.StoreManager.lookup(me.store);
            if (!(me.store === null || !Ext.isDefined(me.store))) {
                // tapping events
                me.mon(me.store, 'datachanged', me.onStoreChanged, me);
                if (me.store.getCount() === 0 || me.store.getCount() < 0) {
                    me.mon(me.store, 'load', me.onStoreLoad, me);
                } else {
                    me.onStoreLoad(me.store);
                }
            }
        }

        Ext.menu.Manager.register(me);
        me.callParent(arguments);
    },

    onStoreLoad: function (store) {
        var me = this;
        me.removeAll(true);
        store = me.store || store;
        store.each(function (item) {
            me.add({
                text: item.get(me.itemRef)
            });
        });
    },

    onStoreChanged: function (store) {
        var me = this;
        store = me.store || store;
    },

    reconfigure: function (store) {
        var me = this;
        store = Ext.StoreManager.lookup(store);

        if (Ext.isDefined(me.store)) {
            me.store.destroyStore();
            me.store = store;
        }

        // tapping events
        me.mon(me.store, 'datachanged', me.onStoreChanged, me);
        if (me.store.getCount() === 0 || me.store.getCount() < 0) {
            me.mon(me.store, 'load', me.onStoreLoad, me);
        } else {
            me.onStoreLoad(me.store);
        }
    },

    getStore: function () {
        return this.store;
    }
});