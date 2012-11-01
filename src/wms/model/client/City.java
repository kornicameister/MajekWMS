package wms.model.client;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import wms.model.basic.NamedPersistenceObject;

@Entity
@Table(name = "city", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@AttributeOverrides(value = { 
		@AttributeOverride(name = "id", column = @Column(name = "idCity", updatable = false, insertable = true, nullable = false)),
		@AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, length = 45, unique = true))
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
