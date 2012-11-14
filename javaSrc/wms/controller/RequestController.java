package wms.controller;

import com.google.gson.*;
import javassist.tools.reflect.Reflection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wms.controller.base.Controller;
import wms.controller.base.annotations.HideAssociation;
import wms.controller.base.annotations.HideField;
import wms.controller.base.extractor.Entity;
import wms.controller.base.extractor.RData;
import wms.controller.base.format.response.CFormat;
import wms.controller.base.format.response.DFormat;
import wms.controller.base.format.response.RFormat;
import wms.controller.base.format.response.UFormat;
import wms.model.BasicPersistentObject;
import wms.model.PersistenceObject;
import wms.model.Warehouse;
import wms.utilities.StringUtils;
import wms.utilities.hibernate.HibernateBridge;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is base class for Controller, that implements all base-level method. And
 * provides ground level read controller and logging.
 *
 * @author kornicameister
 * @created 01-10-2012
 * @file RequestController.java for project MajekWMS
 * @type RequestController
 */
public class RequestController implements Controller {
    protected Session session;

    // -----------RESPONSE----------------/
    protected long processTime = 0l;
    protected Set<BasicPersistentObject> affected = new HashSet<>();
    protected final RData rdata;
    private static ExclusionStrategy HHExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            boolean isHideAssociationEnabled = f.getAnnotation(HideAssociation.class) != null;
            boolean isHideFieldEnabled = f.getAnnotation(HideField.class) != null;
            return (isHideAssociationEnabled || isHideFieldEnabled);
        }

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }
    };
    // -----------RESPONSE----------------/

    private Set<Method> setters;
    private final static Logger logger = Logger
            .getLogger(RequestController.class.getName());

    public RequestController(RData data) {
        super();
        this.rdata = data;
    }

    /**
     * Method wraps CRUD action and commence request processing.
     */
    public void process() {
        logger.info(String.format("Processing [ %s ][ %s ] request, started",
                this.rdata.getAction().toString(), this.rdata.getController()));
        Long startTime = System.nanoTime();
        this.session = HibernateBridge.getSessionFactory().openSession();
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
        this.session.flush();
        this.processTime = System.nanoTime() - startTime;
    }

    // CRUD
    @Override
    public void read() {
        this.session.beginTransaction();
        List<?> data = this.session.createQuery(this.rdata.getReadQuery()).list();
        this.session.getTransaction().commit();
        for (Object o : data) {
            this.affected.add((BasicPersistentObject) o);
        }
    }

    @Override
    public void create() {
        Serializable savedID;
        Collection<? extends BasicPersistentObject> data = this.parsePayload();

        this.session.beginTransaction();
        for (BasicPersistentObject entity : data) {
            PersistenceObject object = (PersistenceObject) entity;
            if (object != null) {
                object.setId(null);
            }
            savedID = this.session.save(entity);
            if (savedID != null) {
                this.affected.add(entity);
            }
        }
        this.session.getTransaction().commit();
    }

    @Override
    public void update() {
        Transaction t = this.session.beginTransaction();
        this.session.flush();
        for (Object entity : this.parsePayload()) {
            this.session.update(entity);
            this.affected.add((BasicPersistentObject) entity);
        }
        t.commit();
    }

    @Override
    public void delete() {
        Transaction t = this.session.beginTransaction();
        for (Object obj : this.parsePayload()) {
            this.session.delete(obj);
            this.affected.add((BasicPersistentObject) obj);
        }
        t.commit();
    }

    // CRUD

    @Override
    public String buildResponse() {
        Gson g = new GsonBuilder()
                .setDateFormat("m-D-y")
                .setExclusionStrategies(HHExclusionStrategy)
                .registerTypeHierarchyAdapter(HibernateProxy.class,
                        new HibernateProxySerializer()).create();
        return this.createResponseString(g);
    }

    /**
     * Method makes the job with creating response to the client side
     *
     * @param g gson
     * @return one of the CRUD responses
     */
    private String createResponseString(Gson g) {
        StringBuilder response = new StringBuilder();
        String controller = this.rdata.getController();
        String entity = this.rdata.getEntity();

        switch (this.rdata.getAction()) {
            case CREATE:
                response.append(g.toJson(new CFormat(true, this.processTime,
                        controller, entity, this.affected), CFormat.class));
                break;
            case READ:
                response.append(g.toJson(new RFormat(true, this.processTime,
                        controller, entity, this.affected), RFormat.class));
                break;
            case UPDATE:
                response.append(g.toJson(new UFormat(true, this.processTime,
                        controller, entity, this.affected), UFormat.class));
                break;
            case DELETE:
                response.append(g.toJson(new DFormat(true, this.processTime,
                        controller, entity, this.affected), DFormat.class));
                break;
        }

        this.session.close();
        this.session = null;

        return response.toString();
    }

    /**
     * Quite handy method that processes {@link RData} payload property being in
     * fact {@link JSONObject} containing some important data to be
     * inserted/read to/from database
     *
     * @return collection of persistent objects
     */
    protected Collection<? extends BasicPersistentObject> parsePayload() {
        RequestController.logger.info("Parsing payload started");
        Gson gson = new GsonBuilder().setDateFormat("y-M-d")
                .setPrettyPrinting().create();
        List<BasicPersistentObject> data = new ArrayList<>();

        JSONObject dataElement;
        BasicPersistentObject entity = null;

        try {
            JSONArray jsonArray = (JSONArray) this.rdata.getPayload().get("data");
            for (Object internalArray : jsonArray) {
                dataElement = (JSONObject) internalArray;

                switch (this.rdata.getAction()) {
                    case UPDATE:
                        entity = this.preUpdateByReflection(dataElement);
                        break;
                    case CREATE:
                        entity = gson.fromJson(dataElement.toJSONString(),
                                this.rdata.getModule().getEntityClass());
                        entity = this.preCreate(entity, dataElement);

                        if (entity instanceof PersistenceObject) {
                            PersistenceObject obj = (PersistenceObject) entity;
                            if (obj != null) {
                                obj.setId(null);
                                entity = obj;
                            }
                        }

                        break;
                    case DELETE:
                        entity = this.preDelete(dataElement);
                        break;
                    default:
                        break;
                    case READ:
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
     * @param jData {@link JSONObject} full of properties that were changed and
     *              must be saved
     * @return valid object if entity indeed differs from passed copy or null if
     *         entity was not modified
     */
    private BasicPersistentObject preUpdateByReflection(JSONObject jData) {
        logger.info(String.format(
                "Update via reflection, payload data = [ %s ]",
                jData.toJSONString()));
        BasicPersistentObject b = (BasicPersistentObject) this.session.get(this.rdata
                .getModule().getEntityClass(), (Serializable) jData.get("id"));

        // this method is called many times still, setters are extracted only
        // once !
        this.extractSetters(b);

        for (Method m : this.setters) {
            String methodName = m.getName(), propertyName;
            String type[] = methodName.split("^(set)");
            if (methodName.contains("set")) {
                propertyName = StringUtils
                        .decapitalizeFirstLetter(type[type.length - 1]);
                Object value = jData.get(propertyName);
                if (value != null && !propertyName.equals("id")) {
                    try {
                        m.invoke(b, this.adjustValueType(value, propertyName));
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
     * Method goes through methods declared in {@link wms.model.BasicPersistentObject}(persistentObject) and
     * cuts off all but setters.
     *
     * @param persistentObject
     */
    private void extractSetters(BasicPersistentObject persistentObject) {

        if (this.setters != null && this.setters.size() > 0) {
            return;
        }

        Method methods[] = persistentObject.getClass().getMethods();
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

        Set<Method> setters = new HashSet<>();
        for (Method method : methods) {
            if (method.getName().contains("set")) {
                setters.add(method);
            }
        }

        logger.info(String.format("Extracted [ %d ] setter from [ %s ]",
                setters.size(), persistentObject.getClass().getSimpleName()));
        this.setters = setters;
    }

    /**
     * Method for implementing controllers that is called when inserting new
     * entity. It is called to update missing dependencies such as associations.
     *
     * @param b
     * @param payloadData
     * @return converted persistent object
     */
    protected BasicPersistentObject preCreate(BasicPersistentObject b,
                                              JSONObject payloadData) {
        return b;
    }

    /**
     * Method for implementing controllers that extends this one. Allows to
     * obtain basic database entity instead of entity extracted from
     * client-side-json.
     * <p/>
     * Entity that is further returned by this method is marked to be deleted.
     *
     * @param payloadData
     * @return
     */
    protected BasicPersistentObject preDelete(JSONObject payloadData) {
        BasicPersistentObject deletable = (BasicPersistentObject) this.session.byId(
                this.rdata.getModule().getEntityClass()).load(
                (Serializable) payloadData.get("id"));
        this.session.flush();
        return deletable;
    }

    /**
     * Method updates model specific fields, fields that needs getting data out
     * of database.
     *
     * @param persistentObject
     * @param payloadData
     * @return object that composite properties had been updated
     */
    protected BasicPersistentObject preUpdateNonPrimitives(BasicPersistentObject persistentObject,
                                                           JSONObject payloadData) {
        return persistentObject;
    }

    /**
     * This method is reused in controllers to provide custom data's type adjustment
     *
     * @param value    to be adjusted
     * @param property must be known by extending controller
     * @return value that type meets requirements of the Hibernate's model
     */
    protected Object adjustValueType(Object value, String property) {
        return value;
    }

    public static String buildErrorResponse() {
        return null;
    }

    /**
     * This method determines Controller type on provided {@link RData}.
     * {@link Entity} object that contains {@link Class} information about
     * module class corresponds to request.
     *
     * @param respData {@link RData} object, as the data source
     * @return valid instance of the {@link RequestController} derived class
     * @see UnitController
     * @see Warehouse
     * @see UnitTypeController
     */
    public static RequestController pickController(RData respData) {
        RequestController controller = null;
        Class<? extends RequestController> controllerClass = respData
                .getModule().getEntityControllerClass();

        try {
            Class<?>[] types = {RData.class};
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
                GsonBuilder gsonBuilder = new GsonBuilder()
                        .registerTypeHierarchyAdapter(HibernateProxy.class, new HibernateProxySerializer())
                        .setExclusionStrategies(HHExclusionStrategy);
                Object valid = src.getHibernateLazyInitializer().getImplementation();
                return gsonBuilder.create().toJsonTree(valid);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
