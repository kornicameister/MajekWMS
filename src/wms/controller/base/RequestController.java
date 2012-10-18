package wms.controller.base;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.tools.reflect.Reflection;

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
import wms.utils.StringUtils;

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
	private Set<Method> sorters;
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
		Collection<? extends BaseEntity> data = this.parsePayload();

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
		for (Object saveable : this.parsePayload()) {
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
			for (Object obj : this.parsePayload()) {
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
	protected Collection<? extends BaseEntity> parsePayload() {
		RequestController.logger.info("Parsing payload started");
		Gson gson = new GsonBuilder().setDateFormat("y-M-d")
				.setPrettyPrinting().create();
		List<BaseEntity> data = new ArrayList<>();

		JSONObject dataElement = null;
		BaseEntity entity = null;

		try {
			JSONArray ddata = (JSONArray) this.rdata.getPayload().get("data");
			for (short i = 0; i < ddata.size(); i++) {
				dataElement = (JSONObject) ddata.get(i);

				switch (this.rdata.getAction()) {
				case UPDATE:
					entity = this.preUpdateByReflection(dataElement);
					break;
				case CREATE:
					entity = this.preCreate(
							entity = (BaseEntity) gson.fromJson(dataElement
									.toJSONString(), this.rdata.getModule()
									.getEntityClass()), dataElement);
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

		RequestController.logger
				.info("Payload parsed, extracted entities count = [ "
						+ data.size() + " ]");
		return data;
	}

	/**
	 * Method, thanks to {@link Reflection} mechanism goes and efficiently
	 * updates only these properties in target class that were changes on the
	 * client side. Still method specific for certain entity must be called in
	 * order to ensure that properties different that simple(ak primitives) will
	 * be set before commencing update to database engine.
	 * 
	 * @param jData
	 *            {@link JSONObject} full of properties that were changed and
	 *            must be saved
	 * @return valid object if entity indeed differs from passed copy or null if
	 *         entity was not modified
	 */
	private BaseEntity preUpdateByReflection(JSONObject jData) {
		logger.info(String.format(
				"Update via reflection, payload data = [ %s ]",
				jData.toJSONString()));
		BaseEntity b = (BaseEntity) this.session.get(this.rdata.getModule()
				.getEntityClass(), (Serializable) jData.get("id"));

		// this method is called many times still, setters are extracted only
		// once !
		this.extractSetters(b);

		for (Method m : this.sorters) {
			String methodName = m.getName(), propertyName = null;
			String type[] = methodName.split("^(set)");
			if (methodName.contains("set")) {
				propertyName = StringUtils
						.decapitalizeFirstLetter(type[type.length - 1]);
				Object value = jData.get(propertyName);
				if (value != null && !propertyName.equals("id")) {
					try {
						m.invoke(b, value);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if (!propertyName.equals("id")) {
					logger.warning(String.format(
							"Unrecognized property [ %s ]", propertyName));
				}
			}
		}
		return this.preUpdateNonPrimitives(b, jData);
	}

	/**
	 * Method goes through methods declared in {@link BaseEntity}(b) and cuts
	 * off all but setters.
	 * 
	 * @param b
	 * @return
	 */
	private void extractSetters(BaseEntity b) {

		if (this.sorters != null && this.sorters.size() > 0) {
			return;
		}

		Method methods[] = b.getClass().getMethods();
		Arrays.sort(methods, new Comparator<Method>() {

			@Override
			public int compare(Method o1, Method o2) {
				String o1Name = o1.getName(), o2Name = o2.getName();

				boolean isO1Setter = o1Name.contains("set"), isO2Setter = o2Name
						.contains("set");

				if (isO1Setter && isO2Setter) {
					return o1Name.compareTo(o2Name);
				} else if (isO1Setter && !isO2Setter) {
					return -1;
				} else if (!isO1Setter && isO2Setter) {
					return 1;
				}
				return 0;
			}
		});

		Set<Method> sortes = new HashSet<>();
		for (Method method : methods) {
			if (method.getName().contains("set")) {
				sortes.add(method);
			}
		}

		logger.info(String.format("Extracted [ %d ] setter from [ %s ]",
				sortes.size(), b.getClass().getSimpleName()));
		this.sorters = sortes;
	}

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

	/**
	 * Method updates model specific fields, fields that needs getting data out
	 * of database.
	 * 
	 * @param b
	 * @param payloadedData
	 * @return
	 */
	protected abstract BaseEntity preUpdateNonPrimitives(BaseEntity b,
			JSONObject payloadedData);

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
