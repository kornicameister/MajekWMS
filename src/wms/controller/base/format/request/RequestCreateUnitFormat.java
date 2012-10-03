package wms.controller.base.format.request;

import java.util.List;

import wms.model.Unit;

public class RequestCreateUnitFormat {
	List<Unit> data;

	public final List<Unit> getData() {
		return this.data;
	}

	public final void setData(List<Unit> data) {
		this.data = data;
	}

}
