package org.kornicameister.wms.model.logic.controllers.adress;

import org.json.simple.JSONObject;
import org.kornicameister.wms.model.logic.RequestController;
import org.kornicameister.wms.server.extractor.RData;
import org.kornicameister.wms.model.hibernate.Address;
import org.kornicameister.wms.model.hibernate.BasicPersistentObject;
import org.kornicameister.wms.model.hibernate.City;

public class AddressController extends RequestController {

    private class ActionData {
        @SuppressWarnings("unused")
        final Long city_id, address_id;
        final Address address;

        public ActionData(BasicPersistentObject b, JSONObject p) {
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
    protected BasicPersistentObject preCreate(BasicPersistentObject b, JSONObject payloadData) {
        ActionData ad = new ActionData(b, payloadData);
        ad.address.setCity((City) this.session.byId(City.class)
                .load(ad.city_id));
        return ad.address;
    }

    @Override
    protected BasicPersistentObject preUpdateNonPrimitives(BasicPersistentObject persistentObject,
                                                           JSONObject payloadData) {
        ActionData ad = new ActionData(persistentObject, payloadData);

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
