package wms.controller.base.format;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import wms.controller.base.CRUD;
import wms.model.basic.PersistenceObject;

import com.google.gson.annotations.Expose;

/**
 * This class wraps basic format of the server response. Without affected
 * entities or anything else.
 * 
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

	@Expose
	protected Set<PersistenceObject> data;

	@Expose
	protected Integer total;

	@Expose
	protected String message;

	public BaseFormat(boolean success, Long time, String handler,
			Set<PersistenceObject> data, CRUD action) {
		this.success = success;
		this.time = TimeUnit.NANOSECONDS.toMillis(time);
		this.handler = handler;
		this.action = action;
		this.data = new HashSet<>(data);
		this.total = this.data.size();
		this.message = new String(String.format(
				"%s took %dms and affected %d rows", this.action.name(),
				this.time, this.total));
	}

	public BaseFormat(boolean success, Long time, String handler,
			PersistenceObject obj, CRUD action) {
		this.success = success;
		this.time = TimeUnit.NANOSECONDS.toMillis(time);
		this.handler = handler;
		this.action = action;
		this.data = new HashSet<>();
		this.data.add(obj);
		this.total = this.data.size();
		this.message = new String(String.format(
				"%s took %dms and affected %d rows", this.action.name(),
				this.time, this.total));
	}
}
