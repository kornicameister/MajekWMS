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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "unit")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Unit extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@Id
	@Column(name = "idUnit", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Long id;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	@NaturalId(mutable = true)
	protected String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Basic
	@Column(name = "size", nullable = false)
	private Long size;

	@Basic
	@Column(name = "maxSize", nullable = false)
	private Long maximumSize;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "warehouse_id", referencedColumnName = "idWarehouse")
	private Warehouse warehouse;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "unittype_id", referencedColumnName = "idUnitType")
	private UnitType type;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "unitProduct", joinColumns = { @JoinColumn(name = "unit_id", referencedColumnName = "idUnit") }, inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "idProduct") })
	private Set<Product> products = new HashSet<>();

	public Unit() {
		super();
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
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

	public final Long getSize() {
		return size;
	}

	public final void setSize(Long size) {
		this.size = size;
	}

	public final Long getMaximumSize() {
		return maximumSize;
	}

	public final void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public final Warehouse getWarehouse() {
		return warehouse;
	}

	public final void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public final UnitType getType() {
		return type;
	}

	public final void setType(UnitType type) {
		this.type = type;
	}

	public final Set<Product> getProducts() {
		return products;
	}

	public final void setProducts(Set<Product> products) {
		this.products = products;
	}

	public void addProduct(Product p) {
		this.products.add(p);
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
		if (warehouse == null) {
			if (other.warehouse != null)
				return false;
		} else if (!warehouse.equals(other.warehouse))
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
