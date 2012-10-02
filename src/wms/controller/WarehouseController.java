package wms.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;

import wms.controller.base.RequestController;
import wms.controller.request.WarehouseRequest;
import wms.controller.response.WarehouseCreateResponse;
import wms.controller.response.WarehouseReadResponse;
import wms.model.BaseEntity;
import wms.model.Warehouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WarehouseController extends RequestController {
	private final HashSet<Warehouse> warehouses = new HashSet<>(0);
	private final static Logger logger = Logger
			.getLogger(WarehouseController.class.getName());

	public WarehouseController(String action) {
		super(action, "from Warehouse");
	}

	public WarehouseController(String action, Map<String, String[]> params) {
		super(action, "from Warehouse", params);
	}

	public WarehouseController(String action, Map<String, String[]> params,
			String payload) {
		super(action, "from Warehouse", params, payload);
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}

	@Override
	public void create() {
		Serializable warehouseId = null;
		ArrayList<Serializable> ids = new ArrayList<>();
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		for (Warehouse w : this.extractWarehouse()) {
			warehouseId = session.save(w);
			if (warehouseId != null) {
				ids.add(warehouseId);
			}
		}
		session.getTransaction().commit();
		this.createdIDS = ids;
	}

	@Override
	protected final String buildReadResponse() {
		WarehouseController.logger
				.info("Detected [READ] action, building corresponding response");
		this.getWarehouses();

		return new Gson()
				.toJson(new WarehouseReadResponse(this.processTime,
						this.warehouses, this.getClass().getName()),
						WarehouseReadResponse.class).toString();
	}

	@Override
	protected String buildCreateResponse() {
		WarehouseController.logger
				.info("Detected [CREATE] action, building corresponding response");

		return new Gson().toJson(
				new WarehouseCreateResponse(this.processTime, this.createdIDS,
						this.getClass().getName()),
				WarehouseCreateResponse.class).toString();
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

	public HashSet<Warehouse> getWarehouses() {
		if (this.lastRead.size() > 0) {
			for (BaseEntity be : this.lastRead) {
				this.warehouses.add((Warehouse) be);
			}
			this.lastRead.clear();
		}
		return warehouses;
	}

	private List<Warehouse> extractWarehouse() {
		Gson gson = new GsonBuilder().setDateFormat("y-M-d")
				.setPrettyPrinting().create();
		WarehouseRequest w = gson
				.fromJson(this.payload, WarehouseRequest.class);
		return w.getWarehouses();
	}
}
