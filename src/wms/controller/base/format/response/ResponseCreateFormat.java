package wms.controller.base.format.response;

import java.util.ArrayList;

import wms.controller.base.format.BaseFormat;

public class ResponseCreateFormat extends BaseFormat {
	final ArrayList<Integer> created;

	public ResponseCreateFormat(Long time, String handler,
			ArrayList<Integer> affectedIDS) {
		super(time, handler);
		this.created = affectedIDS;
	}

	public final ArrayList<Integer> getAffectedIDS() {
		return created;
	}
}
