package org.kornicameister.wms.controller.base;

import com.google.gson.annotations.Expose;
import org.kornicameister.wms.model.BasicPersistentObject;
import org.kornicameister.wms.model.PersistenceObject;

import javax.persistence.Transient;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * This class wraps basic format of the server response. Without affected
 * entities or anything else.
 *
 * @author kornicameister
 */
public class ResponseFormatBody {
    @Transient
    public static final String S_TOOK_DMS_AND_AFFECTED_D_ROWS = "%s took %dms and affected %d rows";

    @Expose
    private final Long time;

    @Expose
    private final String handler;

    @Expose
    private final Boolean success;

    @Expose
    private final CRUD action;

    @Expose
    private final Set<BasicPersistentObject> data = new TreeSet<>(new Comparator<BasicPersistentObject>() {
        @Override
        public int compare(BasicPersistentObject a, BasicPersistentObject b) {
            try {
                PersistenceObject aa = (PersistenceObject) a;
                PersistenceObject bb = (PersistenceObject) b;
                if (aa != null) {
                    return aa.getId().compareTo(bb.getId());
                }
            } catch (ClassCastException e) {
                assert a != null;
                return Integer.compare(a.hashCode(), b.hashCode());
            }
            return 0;
        }
    });

    @Expose
    private Integer total;

    @Expose
    protected String message;

    @Expose
    private final String entity;

    public ResponseFormatBody(Boolean success,
                              Long time,
                              String handler,
                              String entity,
                              Set<BasicPersistentObject> data,
                              CRUD action) {
        this.success = success;
        this.time = TimeUnit.NANOSECONDS.toMillis(time);
        this.handler = handler;
        this.action = action;
        this.entity = entity;

        this.setAffectedData(data);
        this.setMessageContent();
    }

    /**
     * This constructor is most generic one to be used.
     * It provided, and only it, the way to set up message content,
     * that will be further delivered to the client;
     * @param success
     * @param time
     * @param handler
     * @param entity
     * @param message
     * @param data
     * @param action
     */
    public ResponseFormatBody(Boolean success,
                              Long time,
                              String handler,
                              String entity,
                              String message,
                              Set<BasicPersistentObject> data,
                              CRUD action) {
        this.success = success;
        this.time = TimeUnit.NANOSECONDS.toMillis(time);
        this.handler = handler;
        this.action = action;
        this.entity = entity;

        this.setAffectedData(data);
        if(message == null){
            this.setMessageContent();
        }else{
            this.message = message;
        }
    }

    public ResponseFormatBody(Boolean success,
                              Long time,
                              String handler,
                              PersistenceObject obj) {
        this.success = success;
        this.time = TimeUnit.NANOSECONDS.toMillis(time);
        this.handler = handler;
        this.action = CRUD.READ;
        this.entity = "";

        this.setAffectedData(obj);
        this.setMessageContent();
    }

    private void setAffectedData(PersistenceObject obj) {
        this.data.add(obj);
        this.total = this.data.size();
    }

    private void setAffectedData(Set<BasicPersistentObject> data) {
        this.data.addAll(data);
        this.total = this.data.size();
    }

    private void setMessageContent() {
        this.message = String.format(
                S_TOOK_DMS_AND_AFFECTED_D_ROWS, this.action.name(),
                this.time, this.total);
    }
}
