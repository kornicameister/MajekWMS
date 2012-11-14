package wms.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Query;
import org.json.simple.JSONObject;
import wms.controller.base.extractor.RData;
import wms.model.*;
import wms.utilities.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ClientController extends RequestController {
    public Set<Pair<Client, ClientDetails>> data;
    private final static Logger logger = Logger
            .getLogger(ClientController.class.getName());

    public ClientController(RData data) {
        super(data);
    }

    @Override
    public void read() {
        if (! this.rdata.getParameter().containsKey("type")) {
            super.read();
        } else {
            final String type = this.rdata.getParameter().get("type")[0];
            final Query query = this.session.createQuery(this.rdata.getReadQuery() + " where type = :type");
            query.setParameter("type",type);

            logger.info(String.format(
                    "Client's type %s READ request is being processed", type));

            this.session.beginTransaction();
            List<?> data = query.list();
            this.session.getTransaction().commit();

            //saving !
            this.affected.addAll((Collection<? extends BasicPersistentObject>) data);
        }
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b,
                                              JSONObject payloadData) {
        Gson gson = new GsonBuilder().create();
        Client c;
        Long city_id = (Long) ((HashMap<?, ?>) payloadData.get("address")).get("city_id");
        String type_id = (String) ((HashMap<?, ?>) payloadData.get("type")).get("id");

        if (type_id.equals("supplier")) {
            c = gson.fromJson(payloadData.toJSONString(), Supplier.class);
        } else if (type_id.equals("recipient")) {
            c = gson.fromJson(payloadData.toJSONString(), Recipient.class);
        } else {
            throw new RuntimeException(String.format(
                    "%d is not valid client's type", type_id));
        }

        Address address = c.getAddress();
        address.setCity((City) this.session.byId(City.class).load(city_id));
        c.setAddress(address);

        c.getDetails().setClient(c);

        return c;
    }
}
