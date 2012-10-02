package wms.controller.request;

import java.util.ArrayList;
import java.util.List;

import wms.model.Warehouse;

public class WarehouseRequest {
	private List<Warehouse> warehouses;
	
	public WarehouseRequest() {
		this.warehouses = new ArrayList<>();
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
}
