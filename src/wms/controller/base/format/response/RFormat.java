package wms.controller.base.format.response;

import java.util.Set;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.BasicPersistanceObject;

public class RFormat extends BaseFormat {

	public RFormat(boolean success, Long time, String handler,
			Set<BasicPersistanceObject> affected) {
		super(success, time, handler, affected, CRUD.READ);
	}

}
