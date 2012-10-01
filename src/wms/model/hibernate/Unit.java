package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "unit")
public class Unit extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@Id
	@Column(updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idUnit;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	protected String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Basic
	@Column(name = "size", nullable = false)
	private Integer size;

	@Basic
	@Column(name = "maxSize", nullable = false)
	private Integer maximumSize;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fkWarehouse", referencedColumnName = "idWarehouse")
	private Warehouse masterWarehouse;

	@ManyToMany
	@JoinTable(name = "unitProduct", joinColumns = { @JoinColumn(name = "fkUnit", referencedColumnName = "idunit") }, inverseJoinColumns = { @JoinColumn(name = "fkProduct", referencedColumnName = "idProduct") })
	private Set<Product> unitsProducts = new HashSet<>();

	@OneToOne(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UnitType unitType;

	public Unit() {
		super();
	}

	public Unit(Integer idUnit, String name, String description, Integer size,
			Integer maximumSize) {
		super();
		this.idUnit = idUnit;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public Unit(Integer idUnit, String name, String description, Integer size,
			Integer maximumSize, Warehouse masterWarehouse,
			Set<Product> unitsProducts, UnitType unitType) {
		super();
		this.idUnit = idUnit;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
		this.masterWarehouse = masterWarehouse;
		this.unitsProducts = unitsProducts;
		this.unitType = unitType;
	}

	public final Integer getIdUnit() {
		return idUnit;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final Integer getSize() {
		return size;
	}

	public final Integer getMaximumSize() {
		return maximumSize;
	}

	public final Warehouse getMasterWarehouse() {
		return masterWarehouse;
	}

	public final Set<Product> getUnitsProducts() {
		return unitsProducts;
	}

	public final UnitType getUnitType() {
		return unitType;
	}

	public final void setIdUnit(Integer idUnit) {
		this.idUnit = idUnit;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setSize(Integer size) {
		this.size = size;
	}

	public final void setMaximumSize(Integer maximumSize) {
		this.maximumSize = maximumSize;
	}

	public final void setMasterWarehouse(Warehouse masterWarehouse) {
		this.masterWarehouse = masterWarehouse;
	}

	public final void setUnitsProducts(Set<Product> unitsProducts) {
		this.unitsProducts = unitsProducts;
	}

	public final void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((idUnit == null) ? 0 : idUnit.hashCode());
		result = prime * result
				+ ((masterWarehouse == null) ? 0 : masterWarehouse.hashCode());
		result = prime * result
				+ ((maximumSize == null) ? 0 : maximumSize.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result
				+ ((unitType == null) ? 0 : unitType.hashCode());
		result = prime * result
				+ ((unitsProducts == null) ? 0 : unitsProducts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Unit))
			return false;
		Unit other = (Unit) obj;
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
		if (masterWarehouse == null) {
			if (other.masterWarehouse != null)
				return false;
		} else if (!masterWarehouse.equals(other.masterWarehouse))
			return false;
		if (maximumSize == null) {
			if (other.maximumSize != null)
				return false;
		} else if (!maximumSize.equals(other.maximumSize))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (unitType == null) {
			if (other.unitType != null)
				return false;
		} else if (!unitType.equals(other.unitType))
			return false;
		if (unitsProducts == null) {
			if (other.unitsProducts != null)
				return false;
		} else if (!unitsProducts.equals(other.unitsProducts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unit [");
		if (getIdUnit() != null) {
			builder.append("getIdUnit()=");
			builder.append(getIdUnit());
			builder.append(", ");
		}
		if (getName() != null) {
			builder.append("getName()=");
			builder.append(getName());
			builder.append(", ");
		}
		if (getDescription() != null) {
			builder.append("getDescription()=");
			builder.append(getDescription());
			builder.append(", ");
		}
		if (getSize() != null) {
			builder.append("getSize()=");
			builder.append(getSize());
			builder.append(", ");
		}
		if (getMaximumSize() != null) {
			builder.append("getMaximumSize()=");
			builder.append(getMaximumSize());
			builder.append(", ");
		}
		if (getMasterWarehouse() != null) {
			builder.append("getMasterWarehouse()=");
			builder.append(getMasterWarehouse());
			builder.append(", ");
		}
		if (getUnitsProducts() != null) {
			builder.append("getUnitsProducts()=");
			builder.append(getUnitsProducts());
			builder.append(", ");
		}
		if (getUnitType() != null) {
			builder.append("getUnitType()=");
			builder.append(getUnitType());
			builder.append(", ");
		}
		builder.append("getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
