package wms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "unitType", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "abbreviation" }) })
@AttributeOverride(name = "id", column = @Column(name = "idUnitType", updatable = false, insertable = true, nullable = false))
public class UnitType extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -7479798313966564213L;

	@Basic
	@Column(name = "abbreviation", length = 6, nullable = false, unique = true, insertable = true, updatable = true)
	private String abbreviation;

	@Basic
	@Column(name = "description", length = 120, insertable = true, updatable = true)
	private String description;

	@Basic
	@Column(name = "name", length = 20, nullable = false, unique = true, insertable = true, updatable = true)
	private String name;

	@Basic
	@Column(name = "parentType", nullable = true, unique = false, insertable = true, updatable = true)
	private Integer parentType;

	public UnitType() {
		super();
	}

	public synchronized final String getAbbreviation() {
		return abbreviation;
	}

	public synchronized final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public synchronized final String getDescription() {
		return description;
	}

	public synchronized final void setDescription(String description) {
		this.description = description;
	}

	public synchronized final String getName() {
		return name;
	}

	public synchronized final void setName(String name) {
		this.name = name;
	}

	public synchronized final Integer getParentType() {
		return parentType;
	}

	public synchronized final void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	@Override
	public String toString() {
		return "UnitType [abbreviation=" + abbreviation + ", description="
				+ description + ", name=" + name + ", parentType=" + parentType
				+ ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parentType == null) ? 0 : parentType.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentType == null) {
			if (other.parentType != null)
				return false;
		} else if (!parentType.equals(other.parentType))
			return false;
		return true;
	}

}
