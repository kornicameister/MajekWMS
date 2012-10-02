package wms.controller.response;

import java.util.HashSet;

import wms.model.Warehouse;

public class WarehouseReadResponse extends BaseResponse {
	HashSet<Warehouse> warehouses;

	public WarehouseReadResponse(Long time, HashSet<Warehouse> warehouses,
			String handler) {
		super(time, handler);
		this.warehouses = warehouses;
	}
}
