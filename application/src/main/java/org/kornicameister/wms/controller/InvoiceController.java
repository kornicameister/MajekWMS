package org.kornicameister.wms.controller;

import org.json.simple.JSONObject;
import org.kornicameister.wms.controller.base.extractor.RData;
import org.kornicameister.wms.model.BasicPersistentObject;
import org.kornicameister.wms.model.Client;
import org.kornicameister.wms.model.Invoice;
import org.kornicameister.wms.model.InvoiceType;

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
 * @see {@link RequestController}
 */
public class InvoiceController extends RequestController {

    public InvoiceController(RData data) {
        super(data);
    }

    @Override
    BasicPersistentObject preCreate(BasicPersistentObject b,
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
