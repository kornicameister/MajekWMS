package wms.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.basic.PersistenceObject;
import wms.model.client.Address;
import wms.model.client.City;
import wms.model.client.Client;
import wms.model.client.ClientDetails;
import wms.model.client.ClientType;
import wms.model.client.type.Recipient;
import wms.model.client.type.Supplier;
import wms.utilities.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientController extends RequestController {
	public Set<Pair<Client, ClientDetails>> data;
	private final static Logger logger = Logger
			.getLogger(ClientController.class.getName());

	public ClientController(RData data) {
		super(data);
	}

	@Override
	public void read() {
		if (!this.rdata.getParameter().containsKey("type")) {
			super.read();
		} else {
			final String type = this.rdata.getParameter().get("type")[0];
			Query query = null;
			if (type.equals("supplier")) {
				query = this.session.createQuery(this.rdata.getReadQuery()
						+ " where type_id=1");
			} else if (type.equals("recipient")) {
				query = this.session.createQuery(this.rdata.getReadQuery()
						+ " where type_id=2");
			} else {
				logger.warning(String.format("%s is not valid client's type",
						type));
			}
			logger.info(String.format(
					"Client's type %s READ request is being processed", type));
			this.session.beginTransaction();
			for (Object o : query.list()) {
				this.affected.add((PersistenceObject) o);
			}
			this.session.getTransaction().commit();
		}
	}

	@Override
	/**
	 * This method is customized to save client due to the fact
	 * that client's payload contains aggrageted form of the Client model
	 * which does include address, type and details.
	 */
	public void create() {
		Serializable savedID = null;
		@SuppressWarnings("unchecked")
		Collection<? extends Client> data = (Collection<? extends Client>) this
				.parsePayload();

		this.session.beginTransaction();
		for (Client entity : data) {
			savedID = this.session.save(entity);
			if (savedID != null) {
				this.affected.add(entity);
			}
		}
		this.session.getTransaction().commit();
	}

	@Override
	protected PersistenceObject preCreate(PersistenceObject b,
			JSONObject payloadedData) {
		Gson gson = new GsonBuilder().create();
		Client c = null;
		Long city_id = (Long) ((HashMap<?, ?>) payloadedData.get("address"))
				.get("city_id");
		Long type_id = (Long) ((HashMap<?, ?>) payloadedData.get("type"))
				.get("id");

		if (type_id == 1l) {
			c = gson.fromJson(payloadedData.toJSONString(), Supplier.class);
		} else {
			c = gson.fromJson(payloadedData.toJSONString(), Recipient.class);
		}

		Address address = c.getAddress();
		address.setCity((City) this.session.byId(City.class).load(city_id));
		c.setAddress(address);

		c.setType((ClientType) this.session.byId(ClientType.class).load(
				type_id));
		c.getDetails().setClient(c);
		
		return c;
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
