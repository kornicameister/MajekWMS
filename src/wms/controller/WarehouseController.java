package wms.controller;

import wms.controller.base.extractor.RData;

public class WarehouseController extends BasicController {
	public WarehouseController(RData d) {
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
