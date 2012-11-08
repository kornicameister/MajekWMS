package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.extractor.RData;
import wms.model.Address;
import wms.model.City;
import wms.model.PersistenceObject;

public class AddressController extends BasicController {

	private class ActionData {
		@SuppressWarnings("unused")
		Long city_id, address_id;
		Address address;

		public ActionData(PersistenceObject b, JSONObject p) {
			super();
			this.city_id = (Long) p.get("city_id");
			this.address_id = (Long) p.get("id");
			this.address = (Address) b;
		}
	}

	public AddressController(RData data) {
		super(data);
	}

	@Override
	protected PersistenceObject preCreate(PersistenceObject b, JSONObject payloadedData) {
		ActionData ad = new ActionData(b, payloadedData);
		ad.address.setCity((City) this.session.byId(City.class)
				.load(ad.city_id));
		return ad.address;
	}

	@Override
	protected PersistenceObject preUpdateNonPrimitives(PersistenceObject b,
			JSONObject payloadedData) {
		ActionData ad = new ActionData(b, payloadedData);

		if (ad.city_id != null) {
			ad.address.setCity((City) this.session.byId(City.class).load(
					ad.city_id));
		}
		return ad.address;
	}

	@Override
	protected Object adjustValueType(Object value, String property) {
		return value;
	}

}
