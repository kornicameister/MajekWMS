package wms.controller.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import wms.model.BaseEntity;

import com.google.gson.Gson;

/**
 * This is base class for Controller, that implements all base-level method. And
 * provides ground level data controller and logging.
 * 
 * @author kornicameister
 * @created 01-10-2012
 * @file RequestController.java for project MajekWMS
 * @type RequestController
 * 
 */
public abstract class RequestController implements Controller {
	protected final CRUD action;
	protected final SessionFactory sessionFactory = HibernateBridge
			.getSessionFactory();
	protected final String readStatement;
	protected final Map<String, String[]> params;
	protected Collection<? extends BaseEntity> lastRead;
	protected Gson jsonForm;
	protected long processTime = 0l;
	protected ArrayList<Serializable> createdIDS;
	protected final String payload;
	private final static Logger logger = Logger
			.getLogger(RequestController.class.getName());;

	public RequestController(String action, String read) {
		this.action = CRUD.valueOf(action.toUpperCase());
		this.readStatement = read;
		this.params = new HashMap<>(0);
		this.payload = "";
	}

	public RequestController(String action, String readStatement,
			Map<String, String[]> params) {
		super();
		this.action = CRUD.valueOf(action.toUpperCase());
		this.readStatement = readStatement;
		this.params = params;
		this.payload = "";
	}

	public RequestController(String action, String readStatement,
			Map<String, String[]> params, String payload) {
		this.action = CRUD.valueOf(action.toUpperCase());
		this.readStatement = readStatement;
		this.params = params;
		this.payload = payload;
	}

	/**
	 * Method wraps CRUD action and commence request processing.
	 * 
	 * @return NANO time that processing took
	 */
	public void process() {
		logger.info(String.format("Processing [ %s ] request, started",
				this.action.toString()));
		Long startTime = System.nanoTime();
		switch (this.action) {
		case CREATE:
			this.create();
			break;
		case DELETE:
			this.delete();
			break;
		case READ:
			this.read();
			break;
		case UPDATE:
			this.update();
			break;
		}
		this.processTime = System.nanoTime() - startTime;
	}

	@SuppressWarnings("unchecked")
	public List<? extends BaseEntity> read() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		lastRead = session.createQuery(this.readStatement).list();
		session.getTransaction().commit();
		return (List<? extends BaseEntity>) lastRead;
	}

	@Override
	public String buildResponse() {
		switch (this.action) {
		case READ:
			return this.buildReadResponse();
		case CREATE:
			return this.buildCreateResponse();
		case DELETE:
			return this.buildDeleteResponse();
		case UPDATE:
			return this.buildUpdateResponse();
		}
		RequestController.logger
				.warning("No CRUD action found, will respond with empty JSON");
		return "{}";
	}

	protected abstract String buildReadResponse();

	protected abstract String buildCreateResponse();

	protected abstract String buildUpdateResponse();

	protected abstract String buildDeleteResponse();

	public final CRUD getAction() {
		return action;
	}

	public final String getReadStatement() {
		return readStatement;
	}

	public final Map<String, String[]> getParams() {
		return params;
	}

	public final Collection<? extends BaseEntity> getLastRead() {
		return lastRead;
	}

	public final Gson getJsonForm() {
		return jsonForm;
	}

	public long getProcessTime() {
		return processTime;
	}

	public String getPayload() {
		return this.payload;
	}

	public final void setLastRead(Collection<? extends BaseEntity> lastRead) {
		this.lastRead = lastRead;
	}

	public final void setJsonForm(Gson jsonForm) {
		this.jsonForm = jsonForm;
	}

	public void setProcessTime(long processTime) {
		this.processTime = processTime;
	}

}
