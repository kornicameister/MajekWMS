package wms.controller.base.format.response;

import java.util.Set;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.basic.PersistenceObject;

import com.google.gson.annotations.Expose;

public class RFormat extends BaseFormat {
	@Expose
	protected final Set<PersistenceObject> read;

	public RFormat(boolean success, Long time, String handler,
			Set<PersistenceObject> affected) {
		super(success, time, handler, CRUD.READ);
		this.read = affected;
	}

}
