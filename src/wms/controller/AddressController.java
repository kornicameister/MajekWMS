package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;
import wms.model.client.Address;
import wms.model.client.City;

public class AddressController extends RequestController {

	private class ActionData {
		Long city_id, address_id;
		Address address;

		public ActionData(BaseEntity b, JSONObject p) {
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
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = new ActionData(b, payloadedData);
		ad.address.setCity((City) this.session.byId(City.class)
				.load(ad.city_id));
		return ad.address;
	}

	@Override
	protected BaseEntity preDelete(JSONObject payloadedData) {
		ActionData ad = new ActionData(null, payloadedData);
		return (BaseEntity) this.session.byId(Address.class)
				.load(ad.address_id);
	}

	@Override
	protected BaseEntity preUpdateNonPrimitives(BaseEntity b,
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