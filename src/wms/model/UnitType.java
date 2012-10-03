package wms.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "unitType", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "abbreviation" }) })
public class UnitType extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -7479798313966564213L;

	@Id
	@Column(name = "idUnitType", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	private Integer id;

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

	@OneToMany(mappedBy = "type")
	private Set<Unit> units;

	public UnitType() {
		super();
	}

	public UnitType(Integer idUnitType, String abbreviation,
			String description, String name, Integer parentType) {
		super();
		this.id = idUnitType;
		this.abbreviation = abbreviation;
		this.description = description;
		this.name = name;
		this.parentType = parentType;
	}

	public UnitType(Integer idUnitType, String abbreviation,
			String description, String name, Integer parentType, Set<Unit> units) {
		super();
		this.id = idUnitType;
		this.abbreviation = abbreviation;
		this.description = description;
		this.name = name;
		this.parentType = parentType;
		this.units = units;
	}

	public final Integer getIdUnitType() {
		return id;
	}

	public final void setIdUnitType(Integer idUnitType) {
		this.id = idUnitType;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final Integer getParentType() {
		return parentType;
	}

	public final void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	public final Set<Unit> getUnits() {
		return units;
	}

	public final void setUnits(Set<Unit> units) {
		this.units = units;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parentType == null) ? 0 : parentType.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnitType [");
		if (getIdUnitType() != null) {
			builder.append("getIdUnitType()=");
			builder.append(getIdUnitType());
			builder.append(", ");
		}
		if (getAbbreviation() != null) {
			builder.append("getAbbreviation()=");
			builder.append(getAbbreviation());
			builder.append(", ");
		}
		if (getDescription() != null) {
			builder.append("getDescription()=");
			builder.append(getDescription());
			builder.append(", ");
		}
		if (getName() != null) {
			builder.append("getName()=");
			builder.append(getName());
			builder.append(", ");
		}
		if (getParentType() != null) {
			builder.append("getParentType()=");
			builder.append(getParentType());
			builder.append(", ");
		}
		if (getUnits() != null) {
			builder.append("getUnits()=");
			builder.append(getUnits());
			builder.append(", ");
		}
		builder.append("hashCode()=");
		builder.append(hashCode());
		builder.append(", ");
		if (getUpdatedOn() != null) {
			builder.append("getUpdatedOn()=");
			builder.append(getUpdatedOn());
			builder.append(", ");
		}
		builder.append("getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
