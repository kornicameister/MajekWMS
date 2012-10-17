package wms.controller.base.extractor;

import java.util.Map;

import org.json.simple.JSONObject;

import wms.controller.base.CRUD;
import wms.utilities.Pair;

/**
 * Data package for all variables that were extracted from incoming request or
 * response.
 * 
 * @author kornicameister
 * 
 */
public class RData {
	Entity module;
	Map<String, String[]> parameter;
	JSONObject payload;
	Pair<String, Integer> queryKey;
	String[] uri;
	CRUD action;
	
	public String getController() {
		return this.module.getEntityControllerClass().getSimpleName();
	}
	
	public String getReadQuery() {
		return "from " + this.module.toString();
	}

	public final Entity getModule() {
		return module;
	}

	public final void setModule(Entity module) {
		this.module = module;
	}

	public final Map<String, String[]> getParameter() {
		return parameter;
	}

	public final void setParameter(Map<String, String[]> parameter) {
		this.parameter = parameter;
	}

	public final JSONObject getPayload() {
		return payload;
	}

	public final void setPayload(JSONObject payload) {
		this.payload = payload;
	}

	public final Pair<String, Integer> getQueryKey() {
		return queryKey;
	}

	public final void setQueryKey(Pair<String, Integer> queryKey) {
		this.queryKey = queryKey;
	}

	public final String[] getUri() {
		return uri;
	}

	public final void setUri(String[] uri) {
		this.uri = uri;
	}

	public final CRUD getAction() {
		return action;
	}

	public final void setAction(CRUD action) {
		this.action = action;
	}

}
