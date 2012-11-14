package wms.controller;

import wms.controller.base.extractor.RData;

public class MeasureController extends RequestController {

    public MeasureController(RData data) {
        super(data);
    }

    @Override
    protected Object adjustValueType(Object value, String property) {
        if (property.equals("price") || property.equals("quantity")) {
            Double desired = new Double(value.toString());
            return desired;
        }
        return value;
    }

}
