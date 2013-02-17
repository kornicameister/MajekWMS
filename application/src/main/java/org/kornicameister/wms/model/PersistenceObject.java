package org.kornicameister.wms.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * This is a base class for all entities defined in MajekWMS that are known to
 * share same properties.
 *
 * @author kornicameister
 * @created 20-09-2012
 * @file BaseEntity.java for project MajekWMS
 * @type BaseEntity
 */

@MappedSuperclass
abstract public class PersistenceObject extends BasicPersistentObject {
    @Transient
    private static final long serialVersionUID = 8641451013192983600L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    @Expose
    private Long id = null;


    public PersistenceObject() {
        super();
    }

    public synchronized Long getId() {
        return id;
    }

    public synchronized final void setId(Long id) {
        this.id = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof PersistenceObject)) return false;
        if (! super.equals(o)) return false;

        PersistenceObject that = (PersistenceObject) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersistenceObject{" +
                "id=" + id +
                '}';
    }
}
