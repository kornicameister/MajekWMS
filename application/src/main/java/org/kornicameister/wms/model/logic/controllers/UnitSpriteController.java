package org.kornicameister.wms.model.logic.controllers;

import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.BasicPersistentObject;
import org.kornicameister.wms.model.hibernate.Unit;
import org.kornicameister.wms.model.hibernate.UnitSprite;
import org.kornicameister.wms.server.extractor.RData;

import java.io.Serializable;

/**
 * Created for EManager [ org.kornicameister.wms.model.logic.controllers ]
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@ServerController(mapping = "wms/data/unit/sprite", model = UnitSprite.class)
public class UnitSpriteController extends RequestController {

    public UnitSpriteController() {
    }

    public UnitSpriteController(RData data) {
        super(data);
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject basicPersistentObject,
                                              JSONObject payloadData) {
        UnitSprite unitSprite = (UnitSprite) basicPersistentObject;
        unitSprite.setUnit((Unit) this.session.byId(Unit.class).load((Serializable) payloadData.get("unit_id")));
        return unitSprite;
    }

    @Override
    protected Object adjustValueType(Object value, String property) {
        switch (property) {
            case "tileId":
                return new Integer(value.toString());
        }
        return value;
    }
}
