package wms.controller;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import wms.controller.base.extractor.RData;
import wms.model.BasicPersistentObject;
import wms.model.Unit;
import wms.model.UnitType;
import wms.model.Warehouse;
import wms.utilities.Pair;

import java.util.List;

public class UnitController extends RequestController {
    private static final String WHERE_S_WFK = " where %s = :wfk";

    private class ActionData {
        @SuppressWarnings("unused")
        Long fkUnitType, fkWarehouse, idUnit, fkCompany;
        Unit newUnit;

        ActionData(Long fkUnitType, Long fkWarehouse, Long idUnit, Unit newUnit) {
            super();
            this.fkUnitType = fkUnitType;
            this.fkWarehouse = fkWarehouse;
            this.idUnit = idUnit;
            this.newUnit = newUnit;
            this.fkCompany = fkWarehouse;
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

            this.session.flush();

            for (Object o : data) {
                this.affected.add((BasicPersistentObject) o);
            }
        }
    }

    @Override
    protected BasicPersistentObject preUpdateNonPrimitives(BasicPersistentObject persistentObject,
                                                           JSONObject payloadData) {
        Unit unit = (Unit) persistentObject;
        ActionData ad = extractActionData(unit, payloadData);
        Warehouse w;
        UnitType ut;

        if (ad.fkWarehouse != null) {
            w = (Warehouse) this.session.load(Warehouse.class, ad.fkWarehouse);
            if (w != null) {
                this.session.flush();
                ad.newUnit.setWarehouse(w);
            }
        } else if (ad.fkUnitType != null) {
            ut = (UnitType) this.session.load(UnitType.class, ad.fkUnitType);
            if (ut != null) {
                this.session.flush();
                unit.setType(ut);
            }
        }
        return unit;
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b, JSONObject payloadData) {
        ActionData ad = extractActionData(b, payloadData);
        Warehouse w = (Warehouse) this.session.load(Warehouse.class, ad.fkWarehouse);
        UnitType ut = (UnitType) this.session.load(UnitType.class, ad.fkUnitType);

        if (w != null) {
            ad.newUnit.setWarehouse(w);
        }
        if (ut != null) {
            ad.newUnit.setType(ut);
        }

        this.session.flush();
        ad.newUnit.setType(ut);
        ad.newUnit.setWarehouse(w);

        return ad.newUnit;
    }

    private ActionData extractActionData(BasicPersistentObject b, JSONObject payloadData) {
        ActionData ad = new ActionData((Long) payloadData.get("unittype_id"),
                (Long) payloadData.get("warehouse_id"),
                (Long) payloadData.get("id"), (Unit) b);
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
