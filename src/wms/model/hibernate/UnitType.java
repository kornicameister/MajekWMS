package wms.model.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "unitType", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "abbreviation" }) })
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

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Unit unit;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "unit"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idUnit", unique = true, nullable = false)
	private Integer idUnit;

	public UnitType() {
		super();
	}

	public UnitType(String abbreviation, String description, String name) {
		super();
		this.abbreviation = abbreviation;
		this.description = description;
		this.name = name;
	}

	public UnitType(String abbreviation, String description, String name,
			Integer parentType, Unit unit, Integer idUnit) {
		super();
		this.abbreviation = abbreviation;
		this.description = description;
		this.name = name;
		this.parentType = parentType;
		this.unit = unit;
		this.idUnit = idUnit;
	}

	public final String getAbbreviation() {
		return abbreviation;
	}

	public final String getDescription() {
		return description;
	}

	public final String getName() {
		return name;
	}

	public final Integer getParentType() {
		return parentType;
	}

	public final Unit getUnit() {
		return unit;
	}

	public final Integer getIdUnit() {
		return idUnit;
	}

	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	public final void setUnit(Unit unit) {
		this.unit = unit;
	}

	public final void setIdUnit(Integer idUnit) {
		this.idUnit = idUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((idUnit == null) ? 0 : idUnit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parentType == null) ? 0 : parentType.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		if (idUnit == null) {
			if (other.idUnit != null)
				return false;
		} else if (!idUnit.equals(other.idUnit))
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
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnitType [");
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
		if (getUnit() != null) {
			builder.append("getUnit()=");
			builder.append(getUnit());
			builder.append(", ");
		}
		if (getIdUnit() != null) {
			builder.append("getIdUnit()=");
			builder.append(getIdUnit());
			builder.append(", ");
		}
		builder.append("hashCode()=");
		builder.append(hashCode());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
