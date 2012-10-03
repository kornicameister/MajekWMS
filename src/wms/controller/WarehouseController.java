package wms.controller;

import java.util.Map;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;

public class WarehouseController extends RequestController {
	public WarehouseController(CRUD action, Map<String, String[]> params,
			String payload) {
		super(action, params, payload, WarehouseController.class.getName(),
				"from Warehouse");
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}
}
