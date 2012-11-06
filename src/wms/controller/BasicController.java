package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.PersistenceObject;

public class BasicController extends RequestController {

	public BasicController(RData data) {
		super(data);
	}

	@Override
	protected PersistenceObject preCreate(PersistenceObject b, JSONObject payloadedData) {
		return b;
	}

	@Override
	protected PersistenceObject preDelete(JSONObject payloadedData) {
		return null;
	}

	@Override
	protected PersistenceObject preUpdateNonPrimitives(PersistenceObject b,
			JSONObject payloadedData) {
		return b;
	}

	@Override
	protected Object adjustValueType(Object value, String property) {
		return value;
	}

}
