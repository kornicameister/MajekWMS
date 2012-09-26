package wms.model.hibernate;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "unit", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("Unit")
public class Unit extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@Id
	@Column(name = "idUnit", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idUnit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idWarehouse", nullable = false, updatable = true)
	private Warehouse warehouse;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "unit"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idUnitType", unique = true, nullable = false)
	private Integer unitTypeId;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private UnitType unitType;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = Product.class)
	@JoinTable(name = "unitProduct", joinColumns = @JoinColumn(name = "idUnit", referencedColumnName = "idUnit"), inverseJoinColumns = @JoinColumn(name = "idProduct", referencedColumnName = "idProduct"))
	private Collection<Product> productsInUnit = new HashSet<>();

	public Unit() {
		super(); // hibernate
	}

	public Unit(Integer idUnit, Warehouse warehouse, Integer unitTypeId,
			UnitType unitType, Collection<Product> productsInUnit) {
		super();
		this.idUnit = idUnit;
		this.warehouse = warehouse;
		this.unitTypeId = unitTypeId;
		this.unitType = unitType;
		this.productsInUnit = productsInUnit;
	}

	public Unit(String name, String description, Integer size,
			Integer maximumSize, Integer idUnit, Warehouse warehouse,
			Integer unitTypeId, UnitType unitType,
			Collection<Product> productsInUnit) {
		super(name, description, size, maximumSize);
		this.idUnit = idUnit;
		this.warehouse = warehouse;
		this.unitTypeId = unitTypeId;
		this.unitType = unitType;
		this.productsInUnit = productsInUnit;
	}

	public final Integer getIdUnit() {
		return idUnit;
	}

	public final Warehouse getWarehouse() {
		return warehouse;
	}

	public final Integer getUnitTypeId() {
		return unitTypeId;
	}

	public final UnitType getUnitType() {
		return unitType;
	}

	public final Collection<Product> getProductsInUnit() {
		return productsInUnit;
	}

	public final void setIdUnit(Integer idUnit) {
		this.idUnit = idUnit;
	}

	public final void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public final void setUnitTypeId(Integer unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public final void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	public final void setProductsInUnit(Collection<Product> productsInUnit) {
		this.productsInUnit = productsInUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idUnit == null) ? 0 : idUnit.hashCode());
		result = prime * result
				+ ((productsInUnit == null) ? 0 : productsInUnit.hashCode());
		result = prime * result
				+ ((unitType == null) ? 0 : unitType.hashCode());
		result = prime * result
				+ ((unitTypeId == null) ? 0 : unitTypeId.hashCode());
		result = prime * result
				+ ((warehouse == null) ? 0 : warehouse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (idUnit == null) {
			if (other.idUnit != null)
				return false;
		} else if (!idUnit.equals(other.idUnit))
			return false;
		if (productsInUnit == null) {
			if (other.productsInUnit != null)
				return false;
		} else if (!productsInUnit.equals(other.productsInUnit))
			return false;
		if (unitType == null) {
			if (other.unitType != null)
				return false;
		} else if (!unitType.equals(other.unitType))
			return false;
		if (unitTypeId == null) {
			if (other.unitTypeId != null)
				return false;
		} else if (!unitTypeId.equals(other.unitTypeId))
			return false;
		if (warehouse == null) {
			if (other.warehouse != null)
				return false;
		} else if (!warehouse.equals(other.warehouse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unit [idUnit=" + idUnit + ", warehouse=" + warehouse
				+ ", unitTypeId=" + unitTypeId + ", unitType=" + unitType
				+ ", productsInUnit=" + productsInUnit + ", name=" + name
				+ ", getIdUnit()=" + getIdUnit() + ", getWarehouse()="
				+ getWarehouse() + ", getUnitTypeId()=" + getUnitTypeId()
				+ ", getUnitType()=" + getUnitType() + ", getProductsInUnit()="
				+ getProductsInUnit() + ", hashCode()=" + hashCode()
				+ ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getSize()=" + getSize()
				+ ", getMaxSize()=" + getMaxSize() + ", getRatio()="
				+ getRatio() + ", getUpdatedOn()=" + getUpdatedOn()
				+ ", getVersion()=" + getVersion() + ", getClass()="
				+ getClass() + ", toString()=" + super.toString() + "]";
	}

}
