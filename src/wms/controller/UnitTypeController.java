package wms.controller;

import java.util.HashSet;
import java.util.Map;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;
import wms.controller.response.UnitTypeResponse;
import wms.model.BaseEntity;
import wms.model.UnitType;

import com.google.gson.Gson;

public class UnitTypeController extends RequestController {
	private final HashSet<UnitType> units = new HashSet<>(0);

	public UnitTypeController(String action) {
		super(action, "from UnitType");
	}

	public UnitTypeController(String action, Map<String, String[]> params) {
		super(action, "from UnitType", params);
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}

	@Override
	public void create() {
	}

	public HashSet<UnitType> getUnits() {
		if (this.lastRead.size() > 0) {
			for (BaseEntity be : this.lastRead) {
				this.units.add((UnitType) be);
			}
			this.lastRead.clear();
		}
		return units;
	}

	@Override
	public String buildResponse() {
		if (this.action.equals(CRUD.READ)) {
			UnitTypeController.logger
					.info("Detected [READ] action, building corresponding response");
			this.getUnits();

			return new Gson()
					.toJson(new UnitTypeResponse(this.processTime,
							this.units, this.getClass().getName()),
							UnitTypeResponse.class).toString();
		}

		UnitTypeController.logger
				.warning("No CRUD action found, will respond with empty JSON");
		return "{}";
	}
}
