package wms.controller;

import java.io.Serializable;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;
import wms.model.client.Client;

public class ClientController extends RequestController {

	public ClientController(RData data) {
		super(data);
	}

	@Override
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		return b;
	}

	@Override
	protected BaseEntity preDelete(JSONObject payloadedData) {
		return (BaseEntity) this.session.byId(Client.class).load(
				(Serializable) payloadedData.get("id"));
	}

	@Override
	protected BaseEntity preUpdateNonPrimitives(BaseEntity b,
			JSONObject payloadedData) {
		return b;
	}

	@Override
	protected Object adjustValueType(Object value, String property) {
		return value;
	}
}
