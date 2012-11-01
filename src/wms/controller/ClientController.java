package wms.controller;

import java.io.Serializable;
import java.util.Set;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;
import wms.model.client.Client;
import wms.model.client.ClientDetails;
import wms.utilities.Pair;

import com.google.gson.Gson;

public class ClientController extends RequestController {
	public Set<Pair<Client, ClientDetails>> data;

	public ClientController(RData data) {
		super(data);
	}

	@Override
	public void create() {
		Serializable savedID = null;
		this.parsePayload();
		Client c = null;
		ClientDetails cd = null;

		// saving
		this.session.beginTransaction();
		for (Pair<Client,ClientDetails> saveable : this.data) {
			c = saveable.getFirst();
			cd = saveable.getSecond();
			c.setDetails(cd);
			savedID = this.session.save(c);
			if (savedID != null) {
				this.affected.add(c);
			}
		}
		this.session.getTransaction().commit();
		// saving
	}

	@Override
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		/*
		 * payloadedData includes both client and clientDetails
		 */
		ClientDetails cd = new Gson().fromJson(payloadedData.toJSONString(), ClientDetails.class);
		this.data.add(new Pair<Client, ClientDetails>((Client) b, cd));
		
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
