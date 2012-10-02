package wms.controller;

import java.util.HashSet;
import java.util.Map;

import wms.controller.base.CRUD;
import wms.controller.base.RequestController;
import wms.controller.response.WarehouseResponse;
import wms.model.BaseEntity;
import wms.model.Warehouse;

import com.google.gson.Gson;

public class WarehouseController extends RequestController {
	private final HashSet<Warehouse> warehouses = new HashSet<>(0);

	public WarehouseController(String action) {
		super(action, "from Warehouse");
	}

	public WarehouseController(String action, Map<String, String[]> params) {
		super(action, "from Warehouse", params);
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

	@Override
	public String buildResponse() {
		if (this.action.equals(CRUD.READ)) {
			WarehouseController.logger
					.info("Detected [READ] action, building corresponding response");
			this.getWarehouses();

			return new Gson()
					.toJson(new WarehouseResponse(this.processTime,
							this.warehouses, this.getClass().getName()),
							WarehouseResponse.class).toString();
		}

		WarehouseController.logger
				.warning("No CRUD action found, will respond with empty JSON");
		return "{}";
	}

	public HashSet<Warehouse> getWarehouses() {
		if (this.lastRead.size() > 0) {
			for (BaseEntity be : this.lastRead) {
				this.warehouses.add((Warehouse) be);
			}
			this.lastRead.clear();
		}
		return warehouses;
	}
}
