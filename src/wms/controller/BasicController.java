package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BasicPersistanceObject;

public class BasicController extends RequestController {

	public BasicController(RData data) {
		super(data);
	}

	@Override
	protected BasicPersistanceObject preCreate(BasicPersistanceObject b,
			JSONObject payloadedData) {
		return b;
	}

	@Override
	protected BasicPersistanceObject preUpdateNonPrimitives(BasicPersistanceObject b,
			JSONObject payloadedData) {
		return b;
	}

	@Override
	protected Object adjustValueType(Object value, String property) {
		return value;
	}

}
