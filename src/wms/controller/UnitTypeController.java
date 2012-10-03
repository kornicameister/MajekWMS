package wms.controller;

import java.util.Map;

import org.json.simple.JSONObject;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;
import wms.model.BaseEntity;
import wms.model.UnitType;

public class UnitTypeController extends RequestController {
	public UnitTypeController(CRUD action, Map<String, String[]> params,
			String payload) {
		super(action, params, payload, UnitTypeController.class.getName(),
				"from UnitType", UnitType.class);
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}

	@Override
	protected BaseEntity updateMissingDependencies(BaseEntity b, JSONObject payloadedData) {
		return b;
	}

}
