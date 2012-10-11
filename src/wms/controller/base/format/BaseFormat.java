package wms.controller.base.format;

public class BaseFormat {
	final Long time;
	final String handler;
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
