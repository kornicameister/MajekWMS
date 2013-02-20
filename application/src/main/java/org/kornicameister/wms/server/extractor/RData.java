package org.kornicameister.wms.server.extractor;

import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.CRUD;
import org.kornicameister.wms.utilities.Pair;

import java.util.Map;

/**
 * Data package for all variables that were extracted from incoming request or
 * response.
 *
 * @author kornicameister
 */
public final class RData {
    RequestModule module;
    Map<String, String[]> parameter;
    JSONObject payload;
    Pair<String, Integer> queryKey;
    String[] uri;
    CRUD action;

    public String getController() {
        return this.module.entityControllerClass.getName();
    }

    public String getEntity() {
        return this.module.entityClass.getName();
    }

    public String getReadQuery() {
        return "from " + this.module.getEntityClass().getSimpleName();
    }

    public final RequestModule getModule() {
        return module;
    }

    public final Map<String, String[]> getParameter() {
        return parameter;
    }

    public final JSONObject getPayload() {
        return payload;
    }

    public final Pair<String, Integer> getQueryKey() {
        return queryKey;
    }

    public final CRUD getAction() {
        return action;
    }

}
