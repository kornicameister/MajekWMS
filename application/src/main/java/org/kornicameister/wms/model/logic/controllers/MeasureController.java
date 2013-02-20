package org.kornicameister.wms.model.logic.controllers;

import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.Measure;
import org.kornicameister.wms.server.extractor.RData;

@ServerController(mapping = "wms/agent/measure", model = Measure.class)
public class MeasureController extends RequestController {

    public MeasureController() {

    }

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
