package org.kornicameister.wms.model.hibernate;

import javax.persistence.*;


@Entity
@Table(name = "city",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@AttributeOverrides(value = {
        @AttributeOverride(
                name = "id",
                column = @Column(name = "idCity", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(
                name = "name",
                column = @Column(name = "name", insertable = true, updatable = true, length = 45, unique = true))
})
public class City extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = -3110066439211353202L;

    public City() {
        super();
    }

    @Override
    public String toString() {
        return "City [name=" + this.getName() + ", toString()=" + super.toString() + "]";
    }

}
