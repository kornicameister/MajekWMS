package org.kornicameister.wms.model.logic.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.*;
import org.kornicameister.wms.server.extractor.RData;

/**
 * This class provides functionality customized for mastering {@link Company}
 * entity model. At the moment it is required to obtain persistent copy of
 * {@link City} for Address located within Company model.
 *
 * @author kornicameister
 */
@ServerController(mapping = "wms/agent/company", model = Company.class)
public class CompanyController extends RequestController {
    private static final Logger logger = Logger.getLogger(CompanyController.class);

    public CompanyController() {

    }

    public CompanyController(RData data) {
        super(data);
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b, JSONObject payloadData) {
        Company c = (Company) b;
        Gson gson = new GsonBuilder().setDateFormat("y-M-d")
                .setPrettyPrinting().create();
        Warehouse w = gson.fromJson(payloadData.get("warehouse").toString(), Warehouse.class);

        w.setCompanyId(null);
        c.setId(null);

        c.setWarehouse(w);
        w.setCompany(c);

        logger.info(String.format("Saving company as object as follows [ %s ]", c.toString()));

        return c;
    }
}
