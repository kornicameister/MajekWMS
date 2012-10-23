package wms.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;
import wms.model.Measure;
import wms.model.Product;
import wms.model.Unit;

public class ProductController extends RequestController {
	private Map<Unit, HashSet<Product>> unitProduct;
	private final static Logger logger = Logger
			.getLogger(ProductController.class.getName());

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
		HashSet<Product> products = null;
		Unit unit = null;

		this.parsePayload();

		Transaction t = this.session.beginTransaction();
		for (Entry<Unit, HashSet<Product>> up : this.unitProduct.entrySet()) {
			products = up.getValue();
			unit = up.getKey();

			logger.info(String.format(
					"Unit [ %d-%s ] updated with %d products", unit.getId(),
					unit.getName(), products.size()));

			Set<Product> dbProducts = unit.getProducts();
			dbProducts.addAll(products);
			unit.setProducts(dbProducts);
			this.session.saveOrUpdate(unit);
		}
		t.commit();
	}

	@Override
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);

		ad.product.setMeasure((Measure) this.session.byId(Measure.class).load(
				ad.measure_id));
		this.add((Unit) this.session.byId(Unit.class).load(ad.unit_id),
				ad.product);

		return ad.product;
	}

	@Override
	protected BaseEntity preDelete(JSONObject payloadedData) {
		return (BaseEntity) this.session.byId(Product.class).load((Serializable) payloadedData.get("id"));
	}

	@Override
	protected BaseEntity preUpdateNonPrimitives(BaseEntity b,
			JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);

		if (ad.measure_id != null) {
			ad.product.setMeasure((Measure) this.session.byId(Measure.class)
					.load(ad.measure_id));
		}
		return ad.product;
	}

	private ActionData extractActionData(BaseEntity b, JSONObject payloadedData) {
		return new ActionData((Long) payloadedData.get("measure_id"),
				(Long) payloadedData.get("id"),
				(Long) payloadedData.get("unit_id"), (Product) b);
	}

	private void add(final Unit key, final Product value) {
		HashSet<Product> values = this.unitProduct.get(key);
		if (values == null) {
			values = new HashSet<Product>();
			values.add(value);
			this.unitProduct.put(key, values);
		}
		values.add(value);
	}

	@Override
	protected Object adjustValueType(Object value, String property) {
		String stringValue = value.toString();
		if (property.equals("price") || property.equals("quantity")) {
			Double desired = new Double(stringValue);
			return desired;
		} else if (property.equals("tax")) {
			Float desired = new Float(stringValue);
			return desired;
		}
		return value;
	}

}
