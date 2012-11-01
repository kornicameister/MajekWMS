package wms.controller.base.format.response;

import java.util.Set;

import com.google.gson.annotations.Expose;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.BaseEntity;

public class CFormat extends BaseFormat {
	@Expose
	protected final Set<BaseEntity> created;

	public CFormat(boolean success, Long time, String handler,Set<BaseEntity> affected) {
		super(success, time, handler,CRUD.CREATE);
		this.created = affected;
	}
}
