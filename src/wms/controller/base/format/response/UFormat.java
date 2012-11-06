package wms.controller.base.format.response;

import java.util.Set;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.PersistenceObject;

public class UFormat extends BaseFormat {

	public UFormat(boolean success, Long time, String handler,
			Set<PersistenceObject> affected) {
		super(success, time, handler, affected, CRUD.UPDATE);
	}

}
