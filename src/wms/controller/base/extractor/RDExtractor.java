package wms.controller.base.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	 * Extract module which CRUD action concerns
	 * 
	 * @param uri
	 * @return
	 */
	public static String getModuleAction(String[] uri) {
		return uri[uri.length - 1];
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
	public static String getPayload(HttpServletRequest request)
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
		return payloadBuilder.toString();
	}
}
