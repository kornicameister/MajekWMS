package wms.controller.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	/**
	 * Utility method that allows to obtain CRUD action, as the last part of the
	 * request's URI.
	 * 
	 * @param uri
	 * @return a CRUD action description
	 */
	public static String getCRUDAction(String[] uri) {
		return uri[uri.length - 1];
	}

	/**
	 * Extract module which CRUD action concerns
	 * 
	 * @param uri
	 * @return
	 */
	public static String getModuleAction(String[] uri) {
		return uri[uri.length - 2];
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
}
