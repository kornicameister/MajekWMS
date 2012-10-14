package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;

public class WarehouseController extends RequestController {
	public WarehouseController(RData d) {
		super(d);
	}

	@Override
	protected BaseEntity preUpdate(BaseEntity b, JSONObject payloadedData) {
		return b;
	}

	@Override
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		return b;
	}

	@Override
	protected BaseEntity preDelete(JSONObject payloadedData) {
		return null;
	}
}
