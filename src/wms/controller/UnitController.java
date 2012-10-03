package wms.controller;

import java.util.Map;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;
import wms.model.BaseEntity;
import wms.model.Unit;
import wms.model.UnitType;
import wms.model.Warehouse;

public class UnitController extends RequestController {
	public UnitController(CRUD action, Map<String, String[]> params,
			String payload) {
		super(action, params, payload, UnitController.class.getName(),
				"from Unit", Unit.class);
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}

	@Override
	protected BaseEntity updateMissingDependencies(BaseEntity b, JSONObject payloadedData) {
		Long fkUnitType = (Long) payloadedData.get("fkUnitType"),
			 fkWarehouse = (Long) payloadedData.get("fkWarehouse");
		
		Unit unit = (Unit) b;
		UnitType unitType = null;
		Warehouse warehouse = null;

		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		unitType = (UnitType) session.get(UnitType.class, Integer.valueOf(fkUnitType.toString()));
		warehouse = (Warehouse) session.get(Warehouse.class, Integer.valueOf(fkWarehouse.toString()));
		session.getTransaction().commit();
		session.close();
		
		unit.setUnitType(unitType);
		unit.setMasterWarehouse(warehouse);
		
		return unit;
	}

}
