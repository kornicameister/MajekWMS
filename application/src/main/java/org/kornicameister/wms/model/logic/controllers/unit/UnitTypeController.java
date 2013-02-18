package org.kornicameister.wms.model.logic.controllers.unit;

import org.kornicameister.wms.model.logic.RequestController;
import org.kornicameister.wms.server.extractor.RData;

public class UnitTypeController extends RequestController {

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
