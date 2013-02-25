/**
 * Project: MajekWMS
 * User   : kornicameister
 * Date   : 21.10.12
 * Time   : 23:11
 */

Ext.define('WMS.store.UnitSprites', {
    extend      : 'Ext.data.Store',
    requires    : [
        'WMS.model.sprite.Unit'
    ],
    model       : 'WMS.model.sprite.Unit',
    sorters     : [
        {
            property : 'rect_id',
            direction: 'ASC'
        },
        {
            property : 'text_id',
            direction: 'ASC'
        },
        {
            property : 'unit_id',
            direction: 'ASC'
        }
    ],
    autoSync    : true,
    findBySprite: function (sprite) {
        var self = this,
            matchedSprite,
            sprite_id = (Ext.isDefined(sprite['id']) ? sprite['id'] : sprite);

        if ((matchedSprite = self.findByRect(sprite_id)) !== null) {
            return matchedSprite;
        } else if ((matchedSprite = self.findByText(sprite_id)) != null) {
            return matchedSprite;
        } else if ((matchedSprite = self.findByLock(sprite_id)) != null) {
            return matchedSprite;
        } else if ((matchedSprite = self.findByTile(sprite_id)) != null) {
            return matchedSprite;
        } else {
            return undefined;
        }
    },
    findByTile  : function (tile) {
        var self = this,
            record = undefined;
        if (tile.indexOf('ext-') >= 0) {
            record = self.findRecord('tile_id', tile);
        } else {
            record = self.findRecord('tile_id', tile['id']);
        }
        return record;
    },
    findByLock  : function (lock) {
        var self = this,
            record = undefined;
        if (lock.indexOf('ext-') >= 0) {
            record = self.findRecord('lock_id', lock);
        } else {
            record = self.findRecord('lock_id', lock['id']);
        }
        return record;
    },
    findByRect  : function (rect) {
        var self = this,
            record = undefined;
        if (rect.indexOf('ext-') >= 0) {
            record = self.findRecord('rect_id', rect);
        } else {
            record = self.findRecord('rect_id', rect['id']);
        }
        return record;
    },
    findByText  : function (text) {
        var self = this,
            record = undefined;
        if (text.indexOf('ext-') >= 0) {
            record = self.findRecord('text_id', text);
        } else {
            record = self.findRecord('text_id', text['id']);
        }
        return record;
    }
});