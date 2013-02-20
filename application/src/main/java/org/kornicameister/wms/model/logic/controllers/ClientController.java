package org.kornicameister.wms.model.logic.controllers;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.*;
import org.kornicameister.wms.server.extractor.RData;
import org.kornicameister.wms.utilities.Pair;

import java.util.HashMap;
import java.util.Set;

@ServerController(mapping = "wms/agent/client", model = Client.class)
public class ClientController extends RequestController {
    public Set<Pair<Client, ClientDetails>> data;
    private final static Logger logger = Logger
            .getLogger(ClientController.class.getName());


    public ClientController() {

    }

    public ClientController(RData data) {
        super(data);
    }

    @Override
    public void read() {
        if (!this.rdata.getParameter().containsKey("type")) {
            super.read();
        } else {
            final String type = this.rdata.getParameter().get("type")[0];
            final Query query = this.session.createQuery(this.rdata.getReadQuery() + " where clientType_id = (select id from ClientType where type = :type)");
            query.setParameter("type", type);

            logger.info(String.format(
                    "Client's type %s READ request is being processed", type));

            for (Object object : query.list()) {
                if (object instanceof BasicPersistentObject) {
                    this.affected.add((BasicPersistentObject) object);
                }
            }
        }
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b,
                                              JSONObject payloadData) {
        Client c = (Client) b;

        // address
        Long city_id = (Long) ((HashMap<?, ?>) payloadData.get("address")).get("city_id");
        Address address = c.getAddress();
        address.setCity((City) this.session.byId(City.class).load(city_id));
        c.setAddress(address);

        // details
        c.getDetails().setClient(c);

        // clientType
        Long type_id = (Long) ((HashMap<?, ?>) payloadData.get("type")).get("id");
        c.setType((ClientType) this.session.byId(ClientType.class).load(type_id));

        return c;
    }
}
