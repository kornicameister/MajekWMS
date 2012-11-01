package wms.controller.base.format;

import java.util.concurrent.TimeUnit;

import wms.controller.base.CRUD;

import com.google.gson.annotations.Expose;

/**
 * This class wraps basic format of the server response.
 * Without affected entities or anything else.
 * @author kornicameister
 * 
 */
public abstract class BaseFormat {
	@Expose
	protected Long time;
	@Expose
	protected String handler;
	@Expose
	protected Boolean success;
	@Expose
	protected CRUD action;

	public BaseFormat(boolean success, Long time, String handler, CRUD action) {
		this.success = success;
		this.time = TimeUnit.NANOSECONDS.toMillis(time);
		this.handler = handler;
		this.action = action;
	}

}
