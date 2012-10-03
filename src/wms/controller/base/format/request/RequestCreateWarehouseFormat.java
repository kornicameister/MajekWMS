package wms.controller.base.format.request;

import java.util.List;

import wms.model.Warehouse;

public class RequestCreateWarehouseFormat {
	List<Warehouse> data;

	public final List<Warehouse> getData() {
		return this.data;
	}

	public final void setData(List<Warehouse> data) {
		this.data = data;
	}

}
