package wms.controller.base;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import wms.controller.UnitController;
import wms.controller.UnitTypeController;
import wms.controller.base.extractor.Entity;
import wms.controller.base.extractor.RData;
import wms.controller.base.format.response.ResponseCreateFormat;
import wms.controller.base.format.response.ResponseReadFormat;
import wms.controller.hibernate.HibernateBridge;
import wms.model.BaseEntity;
import wms.model.Warehouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
	protected final SessionFactory sessionFactory = HibernateBridge
			.getSessionFactory();
	protected Session session;
	protected Gson jsonForm;
	protected long processTime = 0l;
	protected ArrayList<Long> createdIDS = new ArrayList<>();
	protected ArrayList<BaseEntity> lastRead = new ArrayList<>();
	private Integer updateCount = new Integer(0);
	protected final RData rdata;
	private final static Logger logger = Logger
			.getLogger(RequestController.class.getName());

	public RequestController(RData data) {
		super();
		this.rdata = data;
	}

	/**
	 * Method wraps CRUD action and commence request processing.
	 * 
	 * @return NANO time that processing took
	 */
	public void process() {
		logger.info(String.format("Processing [ %s ][ %s ] request, started",
				this.rdata.getAction().toString(), this.rdata.getController()));
		Long startTime = System.nanoTime();
		this.session = this.sessionFactory.openSession();
		switch (this.rdata.getAction()) {
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
	@Override
	public void read() {
		this.session.beginTransaction();
		List<?> data = this.session.createQuery(this.rdata.getReadQuery())
				.list();
		this.session.getTransaction().commit();
		for (Object o : data) {
			this.lastRead.add((BaseEntity) o);
		}
	}

	@Override
	public void create() {
		logger.entering(this.getClass().getName(), "create");
		Serializable savedID = null;
		Collection<? extends BaseEntity> data = this.extractFromPayload();

		// saving
		this.session.beginTransaction();
		for (Object saveable : data) {
			savedID = this.session.save(saveable);
			if (savedID != null) {
				this.createdIDS.add((Long) savedID);
			}
		}
		this.session.getTransaction().commit();
		// saving

		logger.exiting(this.getClass().getName(), "create");
	}

	@Override
	public void update() {
		logger.entering(this.getClass().getName(), "update");

		Transaction t = this.session.beginTransaction();
		this.session.flush();
		for (Object saveable : this.extractFromPayload()) {
			this.session.update(saveable);
			this.updateCount++;
		}
		t.commit();

		logger.exiting(this.getClass().getName(), "update");
	}

	@Override
	public void delete() {
		Transaction t = this.session.beginTransaction();
		try {
			for (Object obj : this.extractFromPayload()) {
				this.session.delete(obj);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong...");
		}
		t.commit();
	}

	// CRUD

	@Override
	public String buildResponse() {
		Gson g = new GsonBuilder()
				.setPrettyPrinting()
				.setDateFormat("m-D-y")
				.registerTypeHierarchyAdapter(HibernateProxy.class,
						new HibernateProxySerializer()).create();
		return this.createResponseString(g);
	}

	/**
	 * Method makes the job with creating response to the client side
	 * 
	 * @param g
	 *            gson
	 * @return one of the CRUD responses
	 */
	private String createResponseString(Gson g) {
		StringBuilder response = new StringBuilder();
		String controller = this.rdata.getController();

		switch (this.rdata.getAction()) {
		case CREATE:
			response.append(g.toJson(new ResponseCreateFormat(true,
					this.processTime, controller, this.createdIDS),
					ResponseCreateFormat.class));
			break;
		case READ:
			response.append(g.toJson(new ResponseReadFormat(true,
					this.processTime, controller, this.lastRead),
					ResponseReadFormat.class));
			break;
		case UPDATE:
			response.append("{updated: " + this.updateCount + "}");
			break;
		case DELETE:
			response.append("");
			break;
		}

		this.session.flush();
		this.session.close();
		this.session = null;

		return response.toString();
	}

	/**
	 * Quite handy method that processes {@link RData} payload property being in
	 * fact {@link JSONObject} containing some important data to be
	 * inserted/read to/from database
	 * 
	 * @return
	 */
	protected Collection<? extends BaseEntity> extractFromPayload() {
		Gson gson = new GsonBuilder().setDateFormat("y-M-d")
				.setPrettyPrinting().create();
		List<BaseEntity> data = new ArrayList<>();

		JSONObject dataElement = null;
		BaseEntity entity = null;

		try {
			JSONArray ddata = (JSONArray) this.rdata.getPayload().get("data");
			for (short i = 0; i < ddata.size(); i++) {
				dataElement = (JSONObject) ddata.get(i);

				if (this.rdata.getAction() != CRUD.DELETE) {
					entity = (BaseEntity) gson.fromJson(dataElement
							.toJSONString(), this.rdata.getModule()
							.getEntityClass());
				}

				switch (this.rdata.getAction()) {
				case UPDATE:
					entity = this.preUpdate(entity, dataElement);
					break;
				case CREATE:
					entity = this.preCreate(entity, dataElement);
					break;
				case DELETE:
					entity = this.preDelete(dataElement);
					break;
				default:
					break;
				}

				if (entity != null) {
					data.add(entity);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"Something went wrong when decoding [CREATE] request", e);
		}

		return data;
	}

	/**
	 * Method that all derived controllers must implement in order to ensure
	 * that models with fields marked as being in associations were set not
	 * null.
	 * 
	 * @param b
	 * @param payloadedData
	 * @return valid object if entity indeed differs from passed copy or null if
	 *         entity was not modified
	 */
	protected abstract BaseEntity preUpdate(BaseEntity b,
			JSONObject payloadedData);

	/**
	 * Method for implementing controllers that is called when inserting new
	 * entity. It is called to update missing dependencies such as associations.
	 * 
	 * @param b
	 * @param payloadedData
	 * @return
	 */
	protected abstract BaseEntity preCreate(BaseEntity b,
			JSONObject payloadedData);

	/**
	 * Method for implementing controllers that extends this one. Allows to
	 * obtain basic database entity instead of entity extracted from
	 * client-side-json.
	 * 
	 * Entity that is further returned by this method is marked to be deleted.
	 * 
	 * @param payloadedData
	 * @return
	 */
	protected abstract BaseEntity preDelete(JSONObject payloadedData);

	public static String buildErrorResponse() {
		return null;
	}

	/**
	 * This method determines Controller type on provided {@link RData}.
	 * {@link Entity} object that contains {@link Class} informations about
	 * module class corresponds to request.
	 * 
	 * @param respData
	 * @return valid instance of the {@link RequestController} derived class
	 * 
	 * @see UnitController
	 * @see Warehouse
	 * @see UnitTypeController
	 */
	public static RequestController pickController(RData respData) {
		RequestController controller = null;
		Class<? extends RequestController> controllerClass = respData
				.getModule().getEntityControllerClass();

		try {
			Class<?>[] types = { RData.class };
			Constructor<? extends RequestController> constructor = controllerClass
					.getConstructor(types);
			controller = constructor.newInstance(respData);
			logger.info(String.format(
					"Successfully loaded controller for name [ %s ]", respData
							.getModule().toString()));
		} catch (InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			logger.log(Level.SEVERE, String.format(
					"Failed to load controller class for name = [%s]", respData
							.getModule().toString()), e);
		}

		return controller;
	}

	/**
	 * This class implements functionality that can be easily described as
	 * allowing to GSON library to serialize Hibernate entities that contains
	 * many-to-[one,many] associations.
	 * 
	 * @author kornicameister
	 * @see JsonSerializer
	 */
	public class HibernateProxySerializer implements
			JsonSerializer<HibernateProxy> {
		@Override
		public JsonElement serialize(HibernateProxy src, Type typeOfSrc,
				JsonSerializationContext context) {
			try {
				GsonBuilder gsonBuilder = new GsonBuilder();
				// below ensures deep deproxied serialization
				gsonBuilder.registerTypeHierarchyAdapter(HibernateProxy.class,
						new HibernateProxySerializer());
				Object deProxied = src.getHibernateLazyInitializer()
						.getImplementation();
				return gsonBuilder.create().toJsonTree(deProxied);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
