package wms.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

import wms.controller.base.RequestController;
import wms.controller.response.UnitTypeResponse;
import wms.model.BaseEntity;
import wms.model.UnitType;

import com.google.gson.Gson;

public class UnitTypeController extends RequestController {
	private final HashSet<UnitType> units = new HashSet<>(0);
	private final static Logger logger = Logger
			.getLogger(UnitTypeController.class.getName());

	public UnitTypeController(String action) {
		super(action, "from UnitType");
	}

	public UnitTypeController(String action, Map<String, String[]> params) {
		super(action, "from UnitType", params);
	}

	public UnitTypeController(String action, Map<String, String[]> params,
			String payload) {
		super(action, "from UnitType", params, payload);
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
	protected String buildReadResponse() {
		UnitTypeController.logger
				.info("Detected [READ] action, building corresponding response");
		this.getUnits();

		return new Gson().toJson(
				new UnitTypeResponse(this.processTime, this.units, this
						.getClass().getName()), UnitTypeResponse.class)
				.toString();
	}

	@Override
	protected String buildCreateResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String buildUpdateResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String buildDeleteResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
