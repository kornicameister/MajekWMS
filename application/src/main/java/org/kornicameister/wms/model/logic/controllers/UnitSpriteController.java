package org.kornicameister.wms.model.logic.controllers;

import org.kornicameister.wms.cm.CRUD;
import org.kornicameister.wms.cm.annotations.RestMethod;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.annotations.ServerMethod;
import org.kornicameister.wms.model.hibernate.UnitSprite;

import java.util.Set;

/**
 * Created for EManager [ org.kornicameister.wms.model.logic.controllers ]
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@ServerController(mapping = "wms/data/unit/sprite", model = UnitSprite.class)
public class UnitSpriteController {

    @ServerMethod(mapping = "read", method = @RestMethod(rest = CRUD.READ))
    public void addSprites(Set<UnitSprite> sprites) {

    }

}
