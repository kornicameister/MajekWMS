package org.kornicameister.wms.model.logic.controllers.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.kornicameister.wms.model.logic.RequestController;
import org.kornicameister.wms.server.extractor.RData;
import org.kornicameister.wms.model.hibernate.BasicPersistentObject;
import org.kornicameister.wms.model.hibernate.City;
import org.kornicameister.wms.model.hibernate.Company;
import org.kornicameister.wms.model.hibernate.Warehouse;

/**
 * This class provides functionality customized for mastering {@link Company}
 * entity model. At the moment it is required to obtain persistent copy of
 * {@link City} for Address located within Company model.
 *
 * @author kornicameister
 */
public class CompanyController extends RequestController {
    private static final Logger logger = Logger.getLogger(CompanyController.class);

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
