package wms.model.hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "warehouseType", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "abbreviation" }) })
@DiscriminatorValue("UnitType")
public class UnitType extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = -7479798313966564213L;

	@Id
	@Column(name = "idUnitType", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idUnitType;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "unit", cascade = CascadeType.ALL)
	private Unit unit;

	@Column(name = "abbreviation", length = 6, nullable = false, unique = true, insertable = true, updatable = true)
	private String abbreviation;

	@Column(name = "description", length = 120, insertable = true, updatable = true)
	private String description;

	@Column(name = "name", length = 20, nullable = false, unique = true, insertable = true, updatable = true)
	private String name;

	public UnitType() {
		super();
	}

	public final Integer getIdUnitType() {
		return idUnitType;
	}

	public final Unit getUnit() {
		return unit;
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

	public final void setIdUnitType(Integer idUnitType) {
		this.idUnitType = idUnitType;
	}

	public final void setUnit(Unit unit) {
		this.unit = unit;
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

	public UnitType(Integer idUnitType, Unit unit, String abbreviation,
			String description, String name) {
		super();
		this.idUnitType = idUnitType;
		this.unit = unit;
		this.abbreviation = abbreviation;
		this.description = description;
		this.name = name;
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
				+ ((idUnitType == null) ? 0 : idUnitType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (idUnitType == null) {
			if (other.idUnitType != null)
				return false;
		} else if (!idUnitType.equals(other.idUnitType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true && super.equals(obj);
	}

}
