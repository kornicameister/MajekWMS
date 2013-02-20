package org.kornicameister.wms.model.logic.controllers;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.*;
import org.kornicameister.wms.server.extractor.RData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@ServerController(mapping = "wms/agent/product", model = Product.class)
public class ProductController extends RequestController {
    private Map<Unit, HashSet<Product>> unitProduct;
    private final static Logger logger = Logger
            .getLogger(ProductController.class.getName());

    public ProductController() {

    }

    private class ActionData {
        @SuppressWarnings("unused")
        Long measure_id, product_id, unit_id;
        Product product;

        public ActionData(Long measure_id, Long product_id, Long unit_id,
                          Product p) {
            super();
            this.measure_id = measure_id;
            this.product = p;
            this.product_id = product_id;
            this.unit_id = unit_id;
        }
    }

    public ProductController(RData data) {
        super(data);
        this.unitProduct = new HashMap<>();
    }

    /**
     * Method overrides custom creating process by ensuring that not only
     * product table is updated but also information about association between
     * unit and product is provided.
     */
    @Override
    public void create() {
        HashSet<Product> products;
        Unit unit;

        this.parsePayload();

        this.session.beginTransaction();
        for (Entry<Unit, HashSet<Product>> up : this.unitProduct.entrySet()) {
            products = up.getValue();
            unit = up.getKey();

            logger.info(String.format(
                    "Unit [ %d-%s ] updated with %d products", unit.getId(),
                    unit.getName(), products.size()));

            Set<Product> dbProducts = unit.getProducts();
            dbProducts.addAll(products);
            unit.setProducts(dbProducts);
            this.affected.addAll(products);
        }
        this.session.getTransaction().commit();
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b,
                                              JSONObject payloadData) {
        ActionData ad = extractActionData(b, payloadData);

        ad.product.setMeasure((Measure) this.session.byId(Measure.class).load(ad.measure_id));
        this.add((Unit) this.session.byId(Unit.class).load(ad.unit_id), ad.product);
        ad.product.setId(null);

        return ad.product;
    }

    @Override
    protected BasicPersistentObject preUpdateNonPrimitives(
            BasicPersistentObject persistentObject, JSONObject payloadData) {
        ActionData ad = extractActionData(persistentObject, payloadData);

        if (ad.measure_id != null) {
            ad.product.setMeasure((Measure) this.session.byId(Measure.class)
                    .load(ad.measure_id));
        }
        return ad.product;
    }

    private ActionData extractActionData(BasicPersistentObject b,
                                         JSONObject payloadData) {
        return new ActionData((Long) payloadData.get("measure_id"),
                (Long) payloadData.get("id"),
                (Long) payloadData.get("unit_id"), (Product) b);
    }

    private void add(final Unit key, final Product value) {
        HashSet<Product> values = this.unitProduct.get(key);
        if (values == null) {
            values = new HashSet<>();
            values.add(value);
            this.unitProduct.put(key, values);
        }
        values.add(value);
    }

    @Override
    protected Object adjustValueType(Object value, String property) {
        String stringValue = value.toString();
        switch (property) {
            case "price":
            case "quantity":
                value = new Double(stringValue);
                break;
            case "tax":
                value = new Float(stringValue);
                break;
            case "pallets":
                value = new Integer(stringValue);
                break;
        }
        return value;
    }

}
