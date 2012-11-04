package wms.controller;

import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.basic.PersistenceObject;
import wms.model.client.Client;
import wms.model.client.ClientDetails;
import wms.utilities.Pair;

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
	protected PersistenceObject preCreate(PersistenceObject b,
			JSONObject payloadedData) {
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
