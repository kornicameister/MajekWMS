package wms.controller.base.format;

public class BaseFormat {
	final Long time;
	final String handler;

	public BaseFormat(Long time, String handler) {
		super();
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
