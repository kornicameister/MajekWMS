package wms.controller.base.format;

import com.google.gson.annotations.Expose;
import wms.controller.base.CRUD;
import wms.model.BasicPersistentObject;
import wms.model.PersistenceObject;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * This class wraps basic format of the server response. Without affected
 * entities or anything else.
 *
 * @author kornicameister
 */
public abstract class BaseFormat {
    @Expose
    private Long time;

    @Expose
    private String handler;

    @Expose
    private Boolean success;

    @Expose
    private CRUD action;

    @Expose
    private Set<BasicPersistentObject> data;

    @Expose
    private Integer total;

    @Expose
    protected String message;

    @Expose
    private String entity;

    protected BaseFormat(boolean success,
                         Long time,
                         String handler,
                         String entity,
                         Set<BasicPersistentObject> data,
                         CRUD action) {
        this.success = success;
        this.time = TimeUnit.NANOSECONDS.toMillis(time);
        this.handler = handler;
        this.action = action;
        this.data = new TreeSet<>(new Comparator<BasicPersistentObject>() {
            @Override
            public int compare(BasicPersistentObject a, BasicPersistentObject b) {
                try {
                    PersistenceObject aa = (PersistenceObject) a;
                    PersistenceObject bb = (PersistenceObject) b;
                    if (aa != null) {
                        return aa.getId().compareTo(bb.getId());
                    }
                } catch (ClassCastException e) {
                    return Integer.compare(a.hashCode(), b.hashCode());
                }
                return 0;
            }
        });
        this.data.addAll(data);
        this.total = this.data.size();
        this.entity = entity;
        this.message = String.format(
                "%s took %dms and affected %d rows", this.action.name(),
                this.time, this.total);
    }

    public BaseFormat(boolean success, Long time, String handler,
                      PersistenceObject obj) {
        this.success = success;
        this.time = TimeUnit.NANOSECONDS.toMillis(time);
        this.handler = handler;
        this.action = CRUD.READ;
        this.data = new HashSet<>();
        this.data.add(obj);
        this.entity = "";
        this.total = this.data.size();
        this.message = String.format(
                "%s took %dms and affected %d rows", this.action.name(),
                this.time, this.total);
    }
}
