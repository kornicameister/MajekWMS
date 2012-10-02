package wms.controller.response;

import java.io.Serializable;
import java.util.ArrayList;

public class WarehouseCreateResponse extends BaseResponse {
	ArrayList<Serializable> warehouses;

	public WarehouseCreateResponse(Long time, ArrayList<Serializable> ids,
			String handler) {
		super(time, handler);
		this.warehouses = ids;
	}
}
