package wms.controller;

import java.util.HashMap;

import org.json.simple.JSONObject;

import wms.controller.base.extractor.RData;
import wms.model.Address;
import wms.model.City;
import wms.model.Company;
import wms.model.PersistenceObject;

/**
 * This class provides functionality customized for mastering {@link Company}
 * entity model. At the moment it is required to obtain persistent copy of
 * {@link City} for Address located within Company model.
 * 
 * @author kornicameister
 * 
 */
public class CompanyController extends BasicController {

	public CompanyController(RData data) {
		super(data);
	}

	@Override
	protected PersistenceObject preCreate(PersistenceObject b,
			JSONObject payloadedData) {
		Company c = (Company) b;
		Long city_id = (Long) ((HashMap<?, ?>) payloadedData.get("address"))
				.get("city_id");

		Address address = c.getAddress();
		address.setCity((City) this.session.byId(City.class).load(city_id));
		c.setAddress(address);

		return c;
	}

}
