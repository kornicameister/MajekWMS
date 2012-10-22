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
     * @private
     * This variable holds reference to local storage store, that is used
     * to map menu item to attached store item in order to fire custom
     * events
     */
    mapStore: undefined,

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

        // adjusting map store
        me.mapStore = Ext.create('Ext.data.Store', {
            fields: [
                'id', 'itemId', 'storeItem', 'menuItem'
            ],
            proxy : {
                type: 'localstorage'
            }
        });

        // setting the store
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

        // custom events
        me.addEvents(
            /**
             * @event iclick
             * @description Wrapper for Ext.menu.Menu's click event, that despite
             * passing item of the menu that has been clicked passes store item
             * that is tied to it.
             *
             * @param this, Ext.menu.menu
             * @param menuItem
             * @param storeItem
             */
            'iclick'
        );

        // wrappers
        me.mon(me, 'click', me.onItemClickWrapper, me);

        Ext.menu.Manager.register(me);
        me.callParent(arguments);
    },

    onItemClickWrapper: function (me, menuItem) {
        var storeMenuItem = me.mapStore.getById(menuItem['id']);

        if (Ext.isDefined(storeMenuItem)) {
            me.fireEvent('iclick', me, menuItem, storeMenuItem.get('storeItem'));
        }
    },

    onStoreLoad: function (store) {
        var me = this,
            cmp = undefined;

        me.removeAll(true);
        me.mapStore.removeAll(true);
        store = me.store || store;

        store.each(function (item) {
            cmp = me.add({
                text: item.get(me.itemRef)
            });
            me.mapStore.add({
                id       : cmp['id'],
                itemId   : item.getId(),
                storeItem: item,
                menuItem : cmp
            });
        });
    },

    onStoreChanged: function (store) {
        var me = this;
        store = me.store || store;
    },

    checkStore: function (me, store) {
        store = Ext.StoreManager.lookup(store);
        if (Ext.isDefined(me.store)) {
            me.mun(me.store);
            me.store = store;
        } else {
            me.store = store;
        }
        return me.store;
    },

    reconfigure: function (store) {
        var me = this;
        me.store = me.checkStore(me, store);

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