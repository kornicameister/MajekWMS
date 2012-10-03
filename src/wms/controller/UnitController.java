package wms.controller;

import java.util.Map;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;

public class UnitController extends RequestController {
	public UnitController(CRUD action, Map<String, String[]> params,
			String payload) {
		super(action, params, payload, UnitController.class.getName(),
				"from Unit");
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}

}
