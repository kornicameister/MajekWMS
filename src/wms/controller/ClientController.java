package wms.controller;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.json.simple.JSONObject;

import wms.controller.base.extractor.RData;
import wms.model.Address;
import wms.model.BasicPersistanceObject;
import wms.model.City;
import wms.model.Client;
import wms.model.ClientDetails;
import wms.model.ClientType;
import wms.model.Recipient;
import wms.model.Supplier;
import wms.utilities.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientController extends BasicController {
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
				this.affected.add((BasicPersistanceObject) o);
			}
			this.session.getTransaction().commit();
		}
	}

	@Override
	protected BasicPersistanceObject preCreate(BasicPersistanceObject b,
			JSONObject payloadedData) {
		Gson gson = new GsonBuilder().create();
		Client c = null;
		Long city_id = (Long) ((HashMap<?, ?>) payloadedData.get("address")).get("city_id");
		Long type_id = (Long) ((HashMap<?, ?>) payloadedData.get("type")).get("id");

		if (type_id == 1l) {
			c = gson.fromJson(payloadedData.toJSONString(), Supplier.class);
		} else if (type_id == 2l) {
			c = gson.fromJson(payloadedData.toJSONString(), Recipient.class);
		} else {
			throw new RuntimeException(String.format(
					"%d is not valid client's type", type_id));
		}

		Address address = c.getAddress();
		address.setCity((City) this.session.byId(City.class).load(city_id));
		c.setAddress(address);

		c.setType((ClientType) this.session.byId(ClientType.class)
				.load(type_id));
		c.getDetails().setClient(c);

		return c;
	}
}
