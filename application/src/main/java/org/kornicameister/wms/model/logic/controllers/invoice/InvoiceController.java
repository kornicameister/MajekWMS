package org.kornicameister.wms.model.logic.controllers.invoice;

import org.json.simple.JSONObject;
import org.kornicameister.wms.model.hibernate.BasicPersistentObject;
import org.kornicameister.wms.model.hibernate.Client;
import org.kornicameister.wms.model.hibernate.Invoice;
import org.kornicameister.wms.model.hibernate.InvoiceType;
import org.kornicameister.wms.model.logic.RequestController;
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
 * @see {@link org.kornicameister.wms.model.logic.RequestController}
 */
public class InvoiceController extends RequestController {

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
