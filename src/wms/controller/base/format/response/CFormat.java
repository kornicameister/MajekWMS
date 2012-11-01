package wms.controller.base.format.response;

import java.util.Set;

import com.google.gson.annotations.Expose;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.basic.PersistenceObject;

public class CFormat extends BaseFormat {
	@Expose
	protected final Set<PersistenceObject> created;

	public CFormat(boolean success, Long time, String handler,Set<PersistenceObject> affected) {
		super(success, time, handler,CRUD.CREATE);
		this.created = affected;
	}
}
