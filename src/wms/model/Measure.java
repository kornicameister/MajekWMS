package wms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "measure", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@AttributeOverride(name = "id", column = @Column(name = "idMeasure", updatable = false, insertable = true, nullable = false))
public class Measure extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 8140273816811139591L;

	@Basic
	@Column(nullable = false, length = 10)
	private String abbreviation;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 30)
	private String name;

	public Measure() {
		super();
	}

	public synchronized final String getAbbreviation() {
		return abbreviation;
	}

	public synchronized final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Measure [abbreviation=" + abbreviation + ", name=" + name
				+ ", toString()=" + super.toString() + "]";
	}

}
