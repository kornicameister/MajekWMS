package wms.controller;

import java.io.Serializable;
import java.util.Set;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.basic.PersistenceObject;
import wms.model.client.Client;
import wms.model.client.ClientDetails;
import wms.utilities.Pair;

public class ClientController extends RequestController {
	public Set<Pair<Client, ClientDetails>> data;

	public ClientController(RData data) {
		super(data);
	}
	
	@Override
	protected PersistenceObject preCreate(PersistenceObject b, JSONObject payloadedData) {
		return b;
	}

	@Override
	protected PersistenceObject preDelete(JSONObject payloadedData) {
		return (PersistenceObject) this.session.byId(Client.class).load(
				(Serializable) payloadedData.get("id"));
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
