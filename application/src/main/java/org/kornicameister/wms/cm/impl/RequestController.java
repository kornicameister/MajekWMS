package org.kornicameister.wms.cm.impl;

import com.google.gson.*;
import javassist.tools.reflect.Reflection;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kornicameister.wms.annotations.HideAssociation;
import org.kornicameister.wms.annotations.HideField;
import org.kornicameister.wms.cm.CRUD;
import org.kornicameister.wms.cm.ServerControllable;
import org.kornicameister.wms.model.hibernate.BasicPersistentObject;
import org.kornicameister.wms.model.hibernate.PersistenceObject;
import org.kornicameister.wms.model.hibernate.Warehouse;
import org.kornicameister.wms.server.extractor.RData;
import org.kornicameister.wms.server.responses.ResponseFormatBody;
import org.kornicameister.wms.utilities.Pair;
import org.kornicameister.wms.utilities.StringUtils;
import org.kornicameister.wms.utilities.hibernate.HibernateBridge;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This is base class for Controller, that implements all base-level method. And
 * provides ground level read controller and logging.
 *
 * @author kornicameister
 * @created 01-10-2012
 */
public class RequestController implements ServerControllable {
    protected Session session;

    // -----------RESPONSE----------------/
    private long processTime = 0l;
    protected Set<BasicPersistentObject> affected = new HashSet<>();
    protected RData rdata;
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
    /**
     * this variable is placeholder for information
     * about the error
     * produced while processing the request
     */
    private Pair<Boolean, String> errorDataHolder = null;
    private String response;
    // -----------RESPONSE----------------/

    private Set<Method> setters;
    private final static Logger logger = Logger
            .getLogger(RequestController.class.getName());

    public RequestController(RData data) {
        super();
        this.rdata = data;
    }

    public RequestController() {
        //To change body of created methods use File | Settings | File Templates.
    }

    /**
     * Method wraps CRUD action and commence request processing.
     */
    public String process() {
        logger.info(String.format("Processing [ %s ][ %s ] request, started",
                this.rdata.getAction().toString(),
                this.rdata.getController().getClass().getSimpleName()));
        {
            Long startTime = System.nanoTime();
            {
                this.session = HibernateBridge.getSessionFactory().openSession();
                logger.info(String.format("process :: Session [ %s ] executing tasks...", session.toString()));
                {
                    this.session.beginTransaction();
                    this.startCRUDJob();
                    this.buildResponse();
                    this.session.getTransaction().commit();
                }
                this.session.clear();
                this.session.close();
                logger.info(String.format("process :: Session [ %s ] finished with success...", session.toString()));
            }
            this.processTime = System.nanoTime() - startTime;
        }
        logger.info(String.format("Processing [ %s ][ %s ] request, finished took [ %d ms ]",
                this.rdata.getAction().toString(),
                this.rdata.getController().getClass().getSimpleName(),
                TimeUnit.NANOSECONDS.toSeconds(this.processTime)));
        return this.response;
    }

    private void startCRUDJob() {
        final CRUD action = this.rdata.getAction();
        logger.info(String.format("startCRUDJob :: %s executing in progress...", action.toString()));
        switch (action) {
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
        logger.info(String.format("startCRUDJob :: %s execution finished...", action.toString()));
    }

    // CRUD
    @Override
    public void read() {
        Collection data = this.session.createQuery(this.rdata.getReadQuery()).list();

        if ((data != null) && (!data.isEmpty())) {
            for (Object aData : data) {
                if (aData instanceof BasicPersistentObject) {
                    this.affected.add((BasicPersistentObject) aData);
                }
            }
        }
    }

    @Override
    public void create() {
        Serializable savedID;
        Collection<? extends BasicPersistentObject> data = this.parsePayload();

        if (data == null || data.isEmpty()) {
            return;
        }

        for (BasicPersistentObject entity : data) {
            if (entity instanceof PersistenceObject) {
                PersistenceObject object = (PersistenceObject) entity;
                object.setId(null);
            }
            savedID = this.session.save(entity);
            if (savedID != null) {
                this.affected.add(entity);
            }
        }
    }

    @Override
    public void update() {
        for (Object entity : this.parsePayload()) {
            this.session.update(entity);
            this.affected.add((BasicPersistentObject) entity);
        }
    }

    @Override
    public void delete() {
        for (Object obj : this.parsePayload()) {
            this.session.delete(obj);
            this.affected.add((BasicPersistentObject) obj);
        }
    }

    // CRUD

    private void buildResponse() {
        Gson g = new GsonBuilder()
                .setDateFormat("Y-M-d")
                .setExclusionStrategies(HHExclusionStrategy)
                .registerTypeHierarchyAdapter(HibernateProxy.class,
                        new HibernateProxySerializer()).create();
        StringBuilder response = new StringBuilder();

        response.append(g.toJson(new ResponseFormatBody(
                (this.errorDataHolder == null),
                this.processTime,
                this.rdata.getController(),
                this.rdata.getEntity(),
                this.errorDataHolder == null ? null : this.errorDataHolder.getSecond(),
                this.affected,
                this.rdata.getAction()
        ), ResponseFormatBody.class));

        this.response = response.toString();
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

        BasicPersistentObject entity = null;

        try {
            JSONArray jsonArray = (JSONArray) this.rdata.getPayload().get("data");
            for (Object internalArray : jsonArray) {
                if ((entity = getPersistenceObject(gson, (JSONObject) internalArray, entity)) != null) {
                    data.add(entity);
                }
            }
        } catch (Exception e) {
            logger.error(
                    "Something went wrong when decoding [CREATE] request", e);
            this.errorDataHolder = new Pair<>(true, e.getMessage());
        }

        RequestController.logger
                .info("Payload parsed, extracted entities count = [ "
                        + data.size() + " ]");
        return data;
    }

    private BasicPersistentObject getPersistenceObject(Gson gson,
                                                       JSONObject dataElement,
                                                       BasicPersistentObject entity) {
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
                    obj.setId(null);
                    entity = obj;
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
        return entity;
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
                    logger.warn(String.format(
                            "Unrecognized property [ %s ]", propertyName));
                }
            }
        }
        return this.preUpdateNonPrimitives(b, jData);
    }

    /**
     * Method goes through methods declared in {@link org.kornicameister.wms.model.hibernate.BasicPersistentObject}(persistentObject) and
     * cuts off all but setters.
     *
     * @param persistentObject object from which setters will be exported
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
                } else if (isO1Setter) {
                    return -1;
                } else if (isO2Setter) {
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
     * @param basicPersistentObject basic persistence object to be pre created...
     * @param payloadData           json object to be used to retrieve some stuff of the object
     * @return converted persistent object
     */
    protected BasicPersistentObject preCreate(BasicPersistentObject basicPersistentObject,
                                              JSONObject payloadData) {
        return basicPersistentObject;
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
     * @param persistentObject already fixed object
     * @param payloadData      object that holds information about currently processed object
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
     * {@link org.kornicameister.wms.server.extractor.Entity} object that contains {@link Class} information about
     * module class corresponds to request.
     *
     * @param respData {@link RData} object, as the data source
     * @return valid instance of the {@link RequestController} derived class
     * @see org.kornicameister.wms.model.logic.controllers.UnitController
     * @see Warehouse
     * @see org.kornicameister.wms.model.logic.controllers.UnitTypeController
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
            logger.error(String.format(
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
    private class HibernateProxySerializer implements
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
