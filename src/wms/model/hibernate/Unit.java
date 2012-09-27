package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
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
	protected Long idUnit;
	
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
	@JoinColumn(name = "pkWarehouse", referencedColumnName="idWarehouse")
	private Warehouse masterWarehouse;

	@ManyToMany
	@JoinTable(name = "unitProduct",
			joinColumns = {@JoinColumn(name = "fkUnit", referencedColumnName = "idunit")},
			inverseJoinColumns = {@JoinColumn(name = "fkProduct", referencedColumnName = "idProduct")})
	private Set<Product> unitsProducts = new HashSet<>();
	
	private Integer unitTypeId;
	private UnitType unitType;

	public Unit() {
		super();
	}

	public Unit(Long idUnit, String name, String description, Integer size,
			Integer maximumSize) {
		super();
		this.idUnit = idUnit;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public Unit(Long idUnit, Warehouse warehouse, Integer unitTypeId,
			UnitType unitType, Set<Product> productsInUnit, String name,
			String description, Integer size, Integer maximumSize) {
		super();
		this.idUnit = idUnit;
		this.masterWarehouse = warehouse;
		this.unitTypeId = unitTypeId;
		this.unitType = unitType;
		this.unitsProducts = productsInUnit;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public final Long getIdUnit() {
		return idUnit;
	}

	public final Warehouse getWarehouse() {
		return masterWarehouse;
	}

	public final Integer getUnitTypeId() {
		return unitTypeId;
	}

	public final UnitType getUnitType() {
		return unitType;
	}

	public final Set<Product> getProductsInUnit() {
		return unitsProducts;
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

	public final void setIdUnit(Long idUnit) {
		this.idUnit = idUnit;
	}

	public final void setWarehouse(Warehouse warehouse) {
		this.masterWarehouse = warehouse;
	}

	public final void setUnitTypeId(Integer unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public final void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	public final void setProductsInUnit(Set<Product> productsInUnit) {
		this.unitsProducts = productsInUnit;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idUnit == null) ? 0 : idUnit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((unitTypeId == null) ? 0 : unitTypeId.hashCode());
		result = prime * result
				+ ((masterWarehouse == null) ? 0 : masterWarehouse.hashCode());
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
		if (unitTypeId == null) {
			if (other.unitTypeId != null)
				return false;
		} else if (!unitTypeId.equals(other.unitTypeId))
			return false;
		if (masterWarehouse == null) {
			if (other.masterWarehouse != null)
				return false;
		} else if (!masterWarehouse.equals(other.masterWarehouse))
			return false;
		return true;
	}

}
