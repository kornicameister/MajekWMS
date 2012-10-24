package wms.controller.base.format;

import com.google.gson.annotations.Expose;

public class BaseFormat {
	@Expose
	final Long time;
	@Expose
	final String handler;
	@Expose
	final Boolean success;

	public BaseFormat(boolean success, Long time, String handler) {
		super();
		this.success = success;
		this.time = time;
		this.handler = handler;
	}

	public final Long getTime() {
		return time;
	}

	public final String getHandler() {
		return handler;
	}

}
