package wms.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import wms.controller.base.extractor.RData;
import wms.model.PersistenceObject;
import wms.model.Unit;
import wms.model.UnitType;
import wms.model.Warehouse;
import wms.utilities.Pair;

public class UnitController extends BasicController {
	private static final String WHERE_S_WFK = " where %s = :wfk";

	private class ActionData {
		Long fkUnitType, fkWarehouse, idUnit;
		Unit newUnit;

		ActionData(Long fkUnitType, Long fkWarehouse, Long idUnit, Unit newUnit) {
			super();
			this.fkUnitType = fkUnitType;
			this.fkWarehouse = fkWarehouse;
			this.idUnit = idUnit;
			this.newUnit = newUnit;
		}
	}

	public UnitController(RData data) {
		super(data);
	}

	@Override
	public void read() {
		Pair<String, Integer> queryKey = this.rdata.getQueryKey();
		if (queryKey == null) {
			super.read();
		} else {
			Transaction transaction = this.session.beginTransaction();
			Query query = this.session
					.createQuery((this.rdata.getReadQuery() + String.format(
							WHERE_S_WFK, queryKey.getFirst())));
			query.setParameter("wfk", queryKey.getSecond());
			List<?> data = query.list();

			transaction.commit();
			for (Object o : data) {
				this.affected.add((PersistenceObject) o);
			}
		}
	}

	@Override
	protected PersistenceObject preUpdateNonPrimitives(PersistenceObject b,
			JSONObject payloadedData) {
		Unit unit = (Unit) b;
		ActionData ad = extractActionData(unit, payloadedData);

		if (ad.fkWarehouse != null) {
			unit.setWarehouse((Warehouse) this.session.byId(Warehouse.class)
					.load(ad.fkWarehouse));
		} else if (ad.fkUnitType != null) {
			unit.setType((UnitType) this.session.byId(UnitType.class).load(
					ad.fkUnitType));
		}
		return unit;
	}

	@Override
	protected PersistenceObject preCreate(PersistenceObject b, JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);

		ad.newUnit.setType((UnitType) this.session.byId(UnitType.class)
				.getReference(ad.fkUnitType));
		ad.newUnit.setWarehouse((Warehouse) this.session.byId(Warehouse.class)
				.getReference(ad.fkWarehouse));

		return ad.newUnit;
	}

	@Override
	protected PersistenceObject preDelete(JSONObject payloadedData) {
		ActionData ad = extractActionData(null, payloadedData);
		return (PersistenceObject) this.session.byId(Unit.class).load(ad.idUnit);
	}

	private ActionData extractActionData(PersistenceObject b, JSONObject payloadedData) {
		ActionData ad = new ActionData((Long) payloadedData.get("unittype_id"),
				(Long) payloadedData.get("warehouse_id"),
				(Long) payloadedData.get("id"), (Unit) b);
		return ad;
	}

	@Override
	protected Object adjustValueType(Object value, String property) {
		if (property.equals("price") || property.equals("quantity")) {
			Double desired = new Double(value.toString());
			return desired;
		}
		return value;
	}
}
