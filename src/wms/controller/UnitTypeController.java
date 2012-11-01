package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.basic.PersistenceObject;

public class UnitTypeController extends RequestController {
	public UnitTypeController(RData d) {
		super(d);
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
		if (property.equals("price") || property.equals("quantity")) {
			Double desired = new Double(value.toString());
			return desired;
		}
		return value;
	}
}
