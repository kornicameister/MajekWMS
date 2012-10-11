package wms.model;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "unit")
public class Unit extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@Id
	@Column(name = "idUnit", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer id;

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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "warehouse_id", referencedColumnName = "idWarehouse")
	private Warehouse warehouse;

	@Basic
	@Column(name = "warehouse_id", insertable = false, updatable = false)
	private Integer warehouse_id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "unittype_id", referencedColumnName = "idUnitType")
	private UnitType type;

	@Basic
	@Column(name = "unittype_id", insertable = false, updatable = false)
	private Integer unittype_id;

	@ManyToMany
	@JoinTable(name = "unitProduct", joinColumns = { @JoinColumn(name = "unit_id", referencedColumnName = "idUnit") }, inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "idProduct") })
	private Set<Product> products = new HashSet<>();

	public Unit() {
		super();
	}

	public Unit(Integer idUnit, String name, String description, Integer size,
			Integer maximumSize) {
		super();
		this.id = idUnit;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public Unit(Integer id, String name, String description, Integer size,
			Integer maximumSize, Warehouse warehouse, UnitType type,
			Set<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
		this.setWarehouse(warehouse);
		this.setType(type);
		this.products = products;
	}

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Integer getSize() {
		return size;
	}

	public final void setSize(Integer size) {
		this.size = size;
	}

	public final Integer getMaximumSize() {
		return maximumSize;
	}

	public final void setMaximumSize(Integer maximumSize) {
		this.maximumSize = maximumSize;
	}

	public final Warehouse getWarehouse() {
		return warehouse;
	}

	public final void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
		this.warehouse_id = warehouse.getIdWarehouse();
	}

	public final Integer getWarehousId() {
		return warehouse_id;
	}

	public final UnitType getType() {
		return type;
	}

	public final void setType(UnitType type) {
		this.type = type;
		this.unittype_id = type.getIdUnitType();
	}

	public final Integer getUnitTypeId() {
		return unittype_id;
	}

	public final Set<Product> getProducts() {
		return products;
	}

	public final void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((maximumSize == null) ? 0 : maximumSize.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((unittype_id == null) ? 0 : unittype_id.hashCode());
		result = prime * result
				+ ((warehouse == null) ? 0 : warehouse.hashCode());
		result = prime * result
				+ ((warehouse_id == null) ? 0 : warehouse_id.hashCode());
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
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unittype_id == null) {
			if (other.unittype_id != null)
				return false;
		} else if (!unittype_id.equals(other.unittype_id))
			return false;
		if (warehouse == null) {
			if (other.warehouse != null)
				return false;
		} else if (!warehouse.equals(other.warehouse))
			return false;
		if (warehouse_id == null) {
			if (other.warehouse_id != null)
				return false;
		} else if (!warehouse_id.equals(other.warehouse_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Unit [getId()=" + getId() + ", getName()=" + getName()
				+ ", getDescription()=" + getDescription() + ", getSize()="
				+ getSize() + ", getMaximumSize()=" + getMaximumSize()
				+ ", getWarehouse()=" + getWarehouse() + ", getType()="
				+ getType() + ", getProducts()=" + getProducts()
				+ ", hashCode()=" + hashCode() + ", getVersion()="
				+ getVersion() + "]";
	}

}
