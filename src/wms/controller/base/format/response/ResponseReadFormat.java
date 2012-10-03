package wms.controller.base.format.response;

import java.util.ArrayList;

import wms.controller.base.format.BaseFormat;
import wms.model.BaseEntity;

public class ResponseReadFormat extends BaseFormat {
	final ArrayList<BaseEntity> read;

	public ResponseReadFormat(long time, String handler, ArrayList<BaseEntity> lastRead) {
		super(time, handler);
		this.read = lastRead;
	}

	public final ArrayList<BaseEntity> getData() {
		return read;
	}

}
