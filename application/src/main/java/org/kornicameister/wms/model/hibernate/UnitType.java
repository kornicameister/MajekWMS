package org.kornicameister.wms.model.hibernate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@Entity
@Immutable
@Cache(
        region = "unittype_cache",
        usage = CacheConcurrencyStrategy.READ_ONLY
)
@Cacheable(
        value = true
)
@Table(
        name = "unitType",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "abbreviation"})}
)
@AttributeOverrides(value = {
        @AttributeOverride(
                name = "id",
                column = @Column(
                        name = "idUnitType",
                        updatable = false,
                        insertable = true,
                        nullable = false)),
        @AttributeOverride(
                name = "name",
                column = @Column(
                        name = "type",
                        insertable = true,
                        updatable = false,
                        nullable = false,
                        length = 20,
                        unique = true))
})
public class UnitType extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = -7479798313966564213L;

    @Basic
    @Column(name = "abbreviation", length = 6, nullable = false, unique = true, insertable = true, updatable = true)
    private String abbreviation;

    @Basic
    @Column(name = "description", length = 120, insertable = true, updatable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parentType", referencedColumnName = "idUnitType", nullable = true, insertable = true, updatable = true)
    private UnitType parentType;

    public UnitType() {
        super();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((parentType == null) ? 0 : parentType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof UnitType))
            return false;
        UnitType other = (UnitType) obj;
        if (abbreviation == null) {
            if (other.abbreviation != null)
                return false;
        } else if (!abbreviation.equals(other.abbreviation))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (parentType == null) {
            if (other.parentType != null)
                return false;
        } else if (!parentType.equals(other.parentType))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UnitType [");
        if (abbreviation != null)
            builder.append("abbreviation=").append(abbreviation).append(", ");
        if (description != null)
            builder.append("description=").append(description).append(", ");
        if (parentType != null)
            builder.append("parentType=").append(parentType).append(", ");
        if (super.toString() != null)
            builder.append("toString()=").append(super.toString());
        builder.append("]");
        return builder.toString();
    }

}
