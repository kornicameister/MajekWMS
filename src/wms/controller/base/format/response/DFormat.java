package wms.controller.base.format.response;

import java.util.Set;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.BaseEntity;

import com.google.gson.annotations.Expose;

public class DFormat extends BaseFormat {

	@Expose
	protected final Set<BaseEntity> deleted;

	public DFormat(boolean success, Long time, String handler,
			Set<BaseEntity> affected) {
		super(success, time, handler, CRUD.DELETE);
		this.deleted = affected;
	}

}
