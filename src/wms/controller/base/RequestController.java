package wms.controller.base;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import wms.controller.base.format.response.ResponseCreateFormat;
import wms.controller.base.format.response.ResponseReadFormat;
import wms.controller.hibernate.HibernateBridge;
import wms.model.BaseEntity;

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
	protected final CRUD action;
	protected final SessionFactory sessionFactory = HibernateBridge
			.getSessionFactory();
	protected Gson jsonForm;
	protected long processTime = 0l;
	protected final Map<String, String[]> params;
	protected ArrayList<Integer> createdIDS = new ArrayList<>();
	protected ArrayList<BaseEntity> lastRead = new ArrayList<>();
	protected final String payload, controller, readStatement;
	private final Class<? extends BaseEntity> conversionModel;
	private Integer updateCount = new Integer(0);
	private final static Logger logger = Logger
			.getLogger(RequestController.class.getName());

	public RequestController(CRUD action, Map<String, String[]> params,
			String payload, String controller, String readStatement,
			Class<? extends BaseEntity> convertType) {
		super();
		this.action = action;
		this.params = params;
		this.readStatement = readStatement;
		this.payload = payload;
		this.controller = controller;
		this.conversionModel = convertType;
	}

	/**
	 * Method wraps CRUD action and commence request processing.
	 * 
	 * @return NANO time that processing took
	 */
	public void process() {
		logger.info(String.format("Processing [ %s ][ %s ] request, started",
				this.action.toString(), this.controller));
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
	@Override
	public void read() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<?> data = session.createQuery(this.readStatement).list();
		session.getTransaction().commit();
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
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		for (Object saveable : data) {
			savedID = session.save(saveable);
			if (savedID != null) {
				this.createdIDS.add((Integer) savedID);
			}
		}
		session.getTransaction().commit();
		// saving

		logger.exiting(this.getClass().getName(), "create");
	}

	@Override
	public void update() {
		logger.entering(this.getClass().getName(), "update");
		Collection<? extends BaseEntity> data = this.extractFromPayload();

		// savingOrUpdating
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		for (Object saveable : data) {
			session.update(saveable);
			this.updateCount++;
		}
		session.getTransaction().commit();
		// savingOrUpdating

		logger.exiting(this.getClass().getName(), "update");
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
		switch (this.action) {
		case CREATE:
			response.append(g.toJson(new ResponseCreateFormat(true,
					this.processTime, this.controller, this.createdIDS),
					ResponseCreateFormat.class));
			break;
		case READ:
			response.append(g.toJson(new ResponseReadFormat(true,
					this.processTime, this.controller, this.lastRead),
					ResponseReadFormat.class));
			break;
		case UPDATE:
			response.append("{updated: " + this.updateCount + "}");
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
		List<BaseEntity> data = new ArrayList<>();

		JSONObject payloadData = (JSONObject) JSONValue.parse(this.payload), dataElement = null;
		BaseEntity convertedElement = null;

		try {
			JSONArray ddata = (JSONArray) payloadData.get("data");
			for (short i = 0; i < ddata.size(); i++) {
				dataElement = (JSONObject) ddata.get(i);
				convertedElement = (BaseEntity) gson.fromJson(
						dataElement.toJSONString(),
						Class.forName(this.conversionModel.getName()));
				if (convertedElement != null) {
					data.add(this.updateMissingDependencies(convertedElement,
							dataElement));
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"Something went wrong when decoding [CREATE] request", e);
		}

		return data;
	}

	protected abstract BaseEntity updateMissingDependencies(BaseEntity b,
			JSONObject payloadedData);

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
