package wms.controller.base.format.response;

import java.util.ArrayList;

import wms.controller.base.format.BaseFormat;

public class ResponseCreateFormat extends BaseFormat {
	final ArrayList<Long> created;

	public ResponseCreateFormat(boolean success, Long time, String handler,
			ArrayList<Long> createdIDS) {
		super(success, time, handler);
		this.created = createdIDS;
	}

	public final ArrayList<Long> getAffectedIDS() {
		return created;
	}
}
