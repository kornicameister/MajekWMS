package wms.controller.response;

import java.util.HashSet;

import wms.model.UnitType;

public class UnitTypeResponse extends BaseResponse {
	HashSet<UnitType> unitTypes;

	public UnitTypeResponse(Long time, HashSet<UnitType> types, String handler) {
		super(time, handler);
		this.unitTypes = types;
	}
}
