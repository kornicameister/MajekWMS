package org.kornicameister.wms.model.hibernate;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author kornicameister
 */
@MappedSuperclass
public abstract class NamedPersistenceObject extends PersistenceObject {
    @Transient
    private static final long serialVersionUID = -4491787496758100976L;

    @Basic
    private String name = null;

    public NamedPersistenceObject() {
        super();
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof NamedPersistenceObject))
            return false;
        NamedPersistenceObject other = (NamedPersistenceObject) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NamedBaseEntity [name=" + name + ", toString()="
                + super.toString() + "]";
    }

}
