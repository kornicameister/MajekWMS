package wms.controller.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import wms.controller.UnitController;
import wms.controller.UnitTypeController;
import wms.controller.WarehouseController;
import wms.controller.base.format.request.RequestCreateUnitFormat;
import wms.controller.base.format.request.RequestCreateUnitTypeFormat;
import wms.controller.base.format.request.RequestCreateWarehouseFormat;
import wms.controller.base.format.response.ResponseCreateFormat;
import wms.controller.base.format.response.ResponseReadFormat;
import wms.controller.hibernate.HibernateBridge;
import wms.model.BaseEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This is base class for Controller, that implements all base-level method. And
 * provides ground level read controller and logging.
 * 
 * @author kornicameister
 * @created 01-10-2012
 * @file RequestController.java for project MajekWMS
 * @type RequestController
 * 
 */
public abstract class RequestController implements Controller {
	// TODO change variables to private, there is a possibility that CRUD method
	// impl may disappear in Entities' controllers
	protected final CRUD action;
	protected final SessionFactory sessionFactory = HibernateBridge
			.getSessionFactory();
	protected Gson jsonForm;
	protected long processTime = 0l;
	protected final Map<String, String[]> params;
	protected ArrayList<Integer> createdIDS = new ArrayList<>();
	protected ArrayList<BaseEntity> lastRead = new ArrayList<>();
	protected final String payload, controller, readStatement;
	private final static Logger logger = Logger
			.getLogger(RequestController.class.getName());

	public RequestController(CRUD action, Map<String, String[]> params,
			String payload, String controller, String readStatement) {
		super();
		this.action = action;
		this.params = params;
		this.readStatement = readStatement;
		this.payload = payload;
		this.controller = controller;
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

	// CRUD
	public void read() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<?> data = session.createQuery(this.readStatement).list();
		session.getTransaction().commit();
		for (Object o : data) {
			this.lastRead.add((BaseEntity) o);
		}
	}

	public void create() {
		logger.entering(this.getClass().getName(), "create");
		Serializable savedID = null;
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		for (Object saveable : this.extractFromPayload()) {
			savedID = session.save(saveable);
			if (savedID != null) {
				this.createdIDS.add((Integer) savedID);
			}
		}
		session.getTransaction().commit();
		logger.exiting(this.getClass().getName(), "create");
	}

	// CRUD

	@Override
	public String buildResponse() {
		Gson g = new GsonBuilder().setPrettyPrinting().setDateFormat("m-D-y")
				.create();
		StringBuilder response = new StringBuilder();
		switch (this.action) {
		case CREATE:
			response.append(g.toJson(new ResponseCreateFormat(this.processTime,
					this.controller, this.createdIDS),
					ResponseCreateFormat.class));
			break;
		case READ:
			response.append(g.toJson(new ResponseReadFormat(this.processTime,
					this.controller, this.lastRead), ResponseReadFormat.class));
			break;
		default:
			logger.warning("UPDATE/DELETE are currently not supported");
			break;
		}
		return response.toString();
	}

	protected Collection<? extends BaseEntity> extractFromPayload() {
		Gson gson = new GsonBuilder().setDateFormat("y-M-d")
				.setPrettyPrinting().create();
		List<? extends BaseEntity> data = null;
		if (this.controller.equals(UnitController.class.getName())) {
			logger.info("Extracting " + UnitController.class.getName());
			data = gson.fromJson(this.payload, RequestCreateUnitFormat.class)
					.getData();
		} else if (this.controller.equals(UnitTypeController.class.getName())) {
			logger.info("Extracting " + UnitTypeController.class.getName());
			data = gson.fromJson(this.payload,
					RequestCreateUnitTypeFormat.class).getData();
		} else if (this.controller.equals(WarehouseController.class.getName())) {
			logger.info("Extracting " + WarehouseController.class.getName());
			data = gson.fromJson(this.payload,
					RequestCreateWarehouseFormat.class).getData();
		}
		return data;
	}

	public final CRUD getAction() {
		return action;
	}

}
