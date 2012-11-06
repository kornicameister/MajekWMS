package wms.controller;

import wms.controller.base.extractor.RData;

public class UnitTypeController extends BasicController {
	public UnitTypeController(RData d) {
		super(d);
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
