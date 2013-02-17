package org.kornicameister.wms.controller.base.extractor;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.kornicameister.wms.controller.RequestController;
import org.kornicameister.wms.controller.base.CRUD;
import org.kornicameister.wms.model.BasicPersistentObject;
import org.kornicameister.wms.utilities.Pair;
import org.kornicameister.wms.utilities.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestDataExtractor. Class with static method to utilize extracting required
 * parameters of {@link HttpServletRequest}
 *
 * @author kornicameister
 * @created 01-10-2012
 */
public abstract class RDExtractor {
    private final static Logger logger = Logger.getLogger(RDExtractor.class
            .getName());

    public static RData parse(HttpServletRequest req, CRUD action)
            throws IOException {
        RData data = new RData();

        data.uri = getURI(req);
        data.parameter = new HashMap<String, String[]>(getParameter(req));
        data.module = getModuleAction(data.uri);
        data.payload = getPayload(req);
        if (data.parameter.containsKey("filter")) {
            data.queryKey = getQueryKey(data.parameter);
        }
        data.action = action;

        return data;
    }

    private static String[] getURI(HttpServletRequest req) {
        return req.getRequestURI().split("/");
    }

    private static Pair<String, Integer> getQueryKey(
            Map<String, String[]> params) {
        if (params.containsKey("filter")) {
            String[] filterStr = params.get("filter");
            JSONArray obj = (JSONArray) JSONValue.parse(filterStr[0]);
            JSONObject filter = (JSONObject) obj.get(0);

            String property = (String) filter.get("property");
            Long longVal = (Long) filter.get("value");
            Integer value = Integer.decode(longVal.toString());

            return new Pair<>(property, value);
        }
        return null;
    }

    /**
     * Extract module which CRUD action concerns
     *
     * @param uri chunked URL, that is used to determine the module for the request.
     * @return {@link Entity} that describes module used in request
     */
    @SuppressWarnings("unchecked")
    private static RequestModule getModuleAction(String[] uri) {
        String module = uri[uri.length - 1];
        Entity entity = null;
        RequestModule rm = null;
        Class<? extends BasicPersistentObject> moduleEntityClass;

        try {
            Integer.valueOf(module);
            module = uri[uri.length - 2];
            entity = Entity.valueOf(module);
        } catch (Exception e) {
            try {
                logger.info(String.format("Trial 2 :: Using %s as source", module.toUpperCase()));
                entity = Entity.valueOf(module.toUpperCase());
            } catch (IllegalArgumentException e2) {
                logger.info(String.format("Trial 3 :: Using %s as source", StringUtils.capitalizeFirstLetter(module)));
                module = "org.kornicameister.wms.model." + StringUtils.capitalizeFirstLetter(module);
                try {
                    moduleEntityClass = (Class<? extends BasicPersistentObject>) Class.forName(module);
                    rm = new RequestModule(moduleEntityClass, RequestController.class);
                } catch (ClassNotFoundException e1) {
                    logger.error("Total disaster, I don't know what controller you want to use", e1);
                }
            }
        }
        if (rm == null) {
            rm = new RequestModule(entity);
        }
        return rm;
    }

    /**
     * Utility to obtain parameter map out {@link HttpServletRequest} object
     *
     * @param request current client's request
     * @return map with parameters extracted from {@link HttpServletRequest} request.
     */
    private static Map getParameter(HttpServletRequest request) {
        return request.getParameterMap();
    }

    /**
     * This method extracts {@link InputStream}'s read from request payload Does
     * not concerns about any entity type, just extracts payload to string
     * variable.
     *
     * @param request current client's request
     * @return wrapped payload data
     * @throws IOException
     */
    private static JSONObject getPayload(HttpServletRequest request)
            throws IOException {
        StringBuilder payloadBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        char[] charBuffer = new char[128];
        @SuppressWarnings("UnusedAssignment") int bytesRead = -1;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        inputStream));
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    payloadBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                payloadBuilder.append("");
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        String payload = payloadBuilder.toString();
        if (payload.isEmpty()) {
            payload = "{}";
        }

        JSONObject payloadJSON = null;
        try {
            payloadJSON = (JSONObject) JSONValue.parseWithException(payload);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert payloadJSON != null;
        logger.info(String.format("Extracted payload data, size = [ %d ]",
                payloadJSON.size()));
        return payloadJSON;
    }
}
