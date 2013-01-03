package wms.model;

import wms.controller.base.annotations.HideField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This is the most basic superclass
 * that provides two fields commonly reused throughout
 * the whole database-hibernate layer.
 *
 * @author kornicameister
 * @since 0.0.1
 */
@MappedSuperclass
public abstract class BasicPersistentObject implements Serializable {
    private static final long serialVersionUID = - 2635091318105029639L;

    @Version
    @Column(name = "version")
    @HideField
    private Integer version = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedOn")
    @HideField
    private Date updatedOn;

    public BasicPersistentObject() {
        super();
        this.updatedOn = new Date();
    }

    @Override
    public String toString() {
        return "BasicObject [version=" + version + ", updatedOn=" + updatedOn
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (! (obj instanceof BasicPersistentObject))
            return false;
        BasicPersistentObject other = (BasicPersistentObject) obj;
        if (updatedOn == null) {
            if (other.updatedOn != null)
                return false;
        } else if (! updatedOn.equals(other.updatedOn))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (! version.equals(other.version))
            return false;
        return true;
    }

}
