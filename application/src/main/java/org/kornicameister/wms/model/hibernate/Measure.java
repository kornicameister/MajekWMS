package org.kornicameister.wms.model.hibernate;

import javax.persistence.*;


@Entity
@Table(name = "measure",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idMeasure", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = false, nullable = false, length = 45, unique = true))})
public class Measure extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 8140273816811139591L;

    @Basic
    @Column(nullable = false, length = 10)
    private String abbreviation;

    public Measure() {
        super();
    }

    public synchronized final String getAbbreviation() {
        return abbreviation;
    }

    public synchronized final void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((abbreviation == null) ? 0 : abbreviation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Measure))
            return false;
        Measure other = (Measure) obj;
        if (abbreviation == null) {
            if (other.abbreviation != null)
                return false;
        } else if (!abbreviation.equals(other.abbreviation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Measure [");
        if (abbreviation != null)
            builder.append("abbreviation=").append(abbreviation).append(", ");
        if (super.toString() != null)
            builder.append("toString()=").append(super.toString());
        builder.append("]");
        return builder.toString();
    }

}
