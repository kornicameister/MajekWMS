package wms.controller;

import wms.controller.base.extractor.RData;

public class UnitTypeController extends RequestController {
    public UnitTypeController(RData d) {
        super(d);
    }

    @Override
    protected Object adjustValueType(Object value, String property) {
        switch (property) {
            case "price":
            case "quantity":
                Double desired = new Double(value.toString());
                return desired;
        }
        return value;
    }
}
