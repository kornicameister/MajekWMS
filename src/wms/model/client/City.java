package wms.model.client;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

import wms.model.BaseEntity;

@Entity
@Table(name = "city", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@AttributeOverride(name = "id", column = @Column(name = "idCity", updatable = false, insertable = true, nullable = false))
public class City extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -3110066439211353202L;

	@Basic
	@NaturalId
	@Column(name = "name", insertable = true, updatable = true, length = 45, unique = true)
	private String name;

	public City() {
		super();
	}

	public synchronized final String getName() {
		return name;
	}

	public synchronized final void setName(String name) {
		this.name = name;
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
		if (!(obj instanceof City))
			return false;
		City other = (City) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", toString()=" + super.toString() + "]";
	}

}
