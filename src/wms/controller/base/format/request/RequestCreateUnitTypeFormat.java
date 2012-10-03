package wms.controller.base.format.request;

import java.util.List;

import wms.model.UnitType;

public class RequestCreateUnitTypeFormat {

	List<UnitType> data;

	public final List<UnitType> getData() {
		return this.data;
	}

	public final void setData(List<UnitType> data) {
		this.data = data;
	}
}
