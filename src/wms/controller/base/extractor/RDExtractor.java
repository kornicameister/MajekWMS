package wms.controller.base.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import wms.controller.base.CRUD;
import wms.model.BasicPersistanceObject;
import wms.utilities.Pair;
import wms.utilities.StringUtils;

/**
 * RequestDataExtractor. Class with static method to utilize extracting required
 * parameters of {@link HttpServletRequest}
 * 
 * @author kornicameister
 * @created 01-10-2012
 * @file RDExtractor.java for project MajekWMS
 * @type RDExtractor
 * 
 */
public final class RDExtractor {
	private final static Logger logger = Logger.getLogger(RDExtractor.class
			.getName());

	public static RData parse(HttpServletRequest req, CRUD action)
			throws IOException {
		RData data = new RData();

		data.uri = RDExtractor.getURI(req);
		data.parameter = RDExtractor.getParameter(req);
		data.module = RDExtractor.getModuleAction(data.uri);
		data.payload = RDExtractor.getPayload(req);
		if (data.parameter.containsKey("filter")) {
			data.queryKey = RDExtractor.getQueryKey(data.parameter);
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

			return new Pair<String, Integer>(property, value);
		}
		return null;
	}

	/**
	 * Extract module which CRUD action concerns
	 * 
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Entity getModuleAction(String[] uri) {
		String module = uri[uri.length - 1];
		Entity moduleE = null;

		try {
			Integer.valueOf(module);
			module = uri[uri.length - 2];
			moduleE = Entity.valueOf(module);
		} catch (Exception e) {
			try {
				moduleE = Entity.valueOf(module.toUpperCase());
			} catch (IllegalArgumentException e2) {
				moduleE = Entity.BASIC;
				module = "wms.model."
						+ StringUtils.capitalizeFirstLetter(module);
				try {
					moduleE.setEntityClass((Class<? extends BasicPersistanceObject>) Class
							.forName(module));
					logger.info(String
							.format("Couldn't determine controller, will try to use BasicController [ %s ]",
									module));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		logger.info(String.format("Recognized controller - [ %s ]", moduleE));
		return moduleE;
	}

	/**
	 * Utility to obtain parameter map out {@link HttpServletRequest} object
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String[]> getParameter(HttpServletRequest request) {
		return request.getParameterMap();
	}

	/**
	 * This method extracts {@link InputStream}'s read from request payload Does
	 * not concerns about any entity type, just extracts payload to string
	 * variable.
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static JSONObject getPayload(HttpServletRequest request)
			throws IOException {
		StringBuilder payloadBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					payloadBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				payloadBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
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

		logger.info(String.format("Extracted payloaded data, size = [ %d ]",
				payloadJSON.size()));
		return payloadJSON;
	}
}
