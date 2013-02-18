package org.kornicameister.wms.model.logic.controllers.measure;

import org.kornicameister.wms.model.logic.RequestController;
import org.kornicameister.wms.server.extractor.RData;

public class MeasureController extends RequestController {

    public MeasureController(RData data) {
        super(data);
    }

    @Override
    protected Object adjustValueType(Object value, String property) {
        if (property.equals("price") || property.equals("quantity")) {
            return new Double(value.toString());
        }
        return value;
    }

}
