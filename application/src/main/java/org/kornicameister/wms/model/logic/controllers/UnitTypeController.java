package org.kornicameister.wms.model.logic.controllers;

import org.kornicameister.wms.cm.ServerControllable;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.UnitType;
import org.kornicameister.wms.server.extractor.RData;

@ServerController(mapping = "wms/agent/unittype", model = UnitType.class)
public class UnitTypeController extends RequestController implements ServerControllable {

    public UnitTypeController() {

    }

    public UnitTypeController(RData d) {
        super(d);
    }

    @Override
    protected Object adjustValueType(Object value, String property) {
        switch (property) {
            case "price":
            case "quantity":
                return new Double(value.toString());
        }
        return value;
    }
}
