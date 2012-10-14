package wms.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;

import wms.controller.base.RequestController;
import wms.controller.base.extractor.RData;
import wms.model.BaseEntity;
import wms.model.Unit;
import wms.model.UnitType;
import wms.model.Warehouse;
import wms.utilities.Pair;

public class UnitController extends RequestController {
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
				this.lastRead.add((BaseEntity) o);
			}
		}
	}
	
	@Override
	protected BaseEntity preUpdate(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);
		Unit oldUnit = (Unit) this.session.get(Unit.class, ad.idUnit);

		if (!oldUnit.equals(ad.newUnit)) {
			oldUnit.setType((UnitType) this.session.byId(UnitType.class)
					.getReference(ad.fkUnitType));
			oldUnit.setWarehouse((Warehouse) this.session.byId(Warehouse.class)
					.getReference(ad.fkWarehouse));
			oldUnit.setDescription((String) payloadedData.get("description"));
			oldUnit.setMaximumSize((Long) payloadedData.get("maximumSize"));
			oldUnit.setName((String) payloadedData.get("name"));
			oldUnit.setSize((Long) payloadedData.get("size"));
		} else {
			return null;
		}
		return oldUnit;
	}

	@Override
	protected BaseEntity preCreate(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = extractActionData(b, payloadedData);

		ad.newUnit.setType((UnitType) this.session.byId(UnitType.class).getReference(ad.fkUnitType));
		ad.newUnit.setWarehouse((Warehouse) this.session.byId(Warehouse.class).getReference(ad.fkWarehouse));

		return ad.newUnit;
	}
	
	@Override
	protected BaseEntity preDelete(JSONObject payloadedData) {
		ActionData ad = extractActionData(null, payloadedData);
		return (BaseEntity) this.session.byId(Unit.class).load(ad.idUnit);
	}

	private ActionData extractActionData(BaseEntity b, JSONObject payloadedData) {
		ActionData ad = new ActionData((Long) payloadedData.get("unittype_id"),
				(Long) payloadedData.get("warehouse_id"),
				(Long) payloadedData.get("id"), (Unit) b);
		return ad;
	}
}
