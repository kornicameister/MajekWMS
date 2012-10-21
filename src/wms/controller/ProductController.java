package wms.controller;

import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;
import wms.model.Measure;
import wms.model.Product;
import wms.model.Unit;

public class ProductController extends RequestController {

	private class ActionData {
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
	}

	@Override
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);

		ad.product.setMeasure((Measure) this.session.byId(Measure.class).load(
				ad.measure_id));

		return ad.product;
	}

	@Override
	protected BaseEntity preDelete(JSONObject payloadedData) {
		ActionData ad = extractActionData(null, payloadedData);
		return (BaseEntity) this.session.byId(Product.class)
				.load(ad.product_id);
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

	@Override
	protected BaseEntity postCreate(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);

		Unit u = (Unit) this.session.byId(Unit.class).load(ad.unit_id);
		u.addProduct((Product) b);
		return u;
	}

}
