package wms.controller;

import java.util.Map;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;

public class UnitTypeController extends RequestController {
	public UnitTypeController(CRUD action, Map<String, String[]> params,
			String payload) {
		super(action, params, payload, UnitTypeController.class.getName(),
				"from UnitType");
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}

}
