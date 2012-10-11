package wms.controller;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;
import wms.model.BaseEntity;
import wms.model.Unit;
import wms.model.UnitType;
import wms.model.Warehouse;
import wms.utilities.Pair;

public class UnitController extends RequestController {
	private static final String WHERE_S_WFK = " where %s = :wfk";
	private Pair<String, Integer> warehouseId;

	public UnitController(CRUD action, Map<String, String[]> params,
			String payload) {
		super(action, params, payload, UnitController.class.getName(),
				"from Unit", Unit.class);

		// extract warehouse foreign key
		if (this.params.containsKey("filter")) {
			this.getWarehouseFK();
		}
	}

	private void getWarehouseFK() {
		String[] filterStr = this.params.get("filter");
		JSONArray obj = (JSONArray) JSONValue.parse(filterStr[0]);
		JSONObject filter = (JSONObject) obj.get(0);

		String property = (String) filter.get("property");
		Long longVal = (Long) filter.get("value");
		Integer value = Integer.decode(longVal.toString());

		this.warehouseId = new Pair<String, Integer>(property, value);
	}

	@Override
	public void read() {
		if (this.warehouseId == null) {
			super.read();
		} else {
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery((this.readStatement + String
					.format(WHERE_S_WFK, this.warehouseId.getFirst())));
			query.setParameter("wfk", this.warehouseId.getSecond());
			List<?> data = query.list();

			transaction.commit();
			for (Object o : data) {
				this.lastRead.add((BaseEntity) o);
			}
		}
	}

	@Override
	public void delete() {
	}

	@Override
	protected BaseEntity updateMissingDependencies(BaseEntity b,
			JSONObject payloadedData) {
		Long fkUnitType = (Long) payloadedData.get("unittype_id"), fkWarehouse = (Long) payloadedData
				.get("warehouse_id");

		Unit unit = (Unit) b;
		UnitType unitType = null;
		Warehouse warehouse = null;

		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		unitType = (UnitType) session.get(UnitType.class,
				Integer.valueOf(fkUnitType.toString()));
		warehouse = (Warehouse) session.get(Warehouse.class,
				Integer.valueOf(fkWarehouse.toString()));
		session.getTransaction().commit();
		session.close();

		unit.setType(unitType);
		unit.setWarehouse(warehouse);

		return unit;
	}

}
