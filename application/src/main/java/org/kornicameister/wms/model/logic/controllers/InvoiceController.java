package org.kornicameister.wms.model.logic.controllers;

import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.ServerControllable;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.model.hibernate.*;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.server.extractor.RData;

/**
 * User: kornicameister
 * Date: 23.12.12
 * Time: 20:44
 */

/**
 * Class that handles providing new valid
 * Invoice. It utilizes setting client and type for
 * create action;
 *
 * @author kornicameister
 * @version 0.1
 * @see {@link org.kornicameister.wms.cm.impl.RequestController}
 */
@ServerController(mapping = "wms/agent/invoice", model = Invoice.class)
public class InvoiceController extends RequestController implements ServerControllable {

    public InvoiceController() {

    }

    public InvoiceController(RData data) {
        super(data);
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b,
                                              JSONObject payloadData) {
        Invoice i = (Invoice) b;

        // assigning client
        Long client_id = (Long) payloadData.get("client_id");
        i.setClient((Client) this.session.byId(Client.class).load(client_id));

        // assigning type
        Long type_id = (Long) payloadData.get("type_id");
        i.setType((InvoiceType) this.session.byId(InvoiceType.class).load(type_id));

        return i;
    }
}
