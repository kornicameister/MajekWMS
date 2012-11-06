package wms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "unit")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@AttributeOverrides(value = {
		@AttributeOverride(name = "id", column = @Column(name = "idUnit", updatable = false, insertable = true, nullable = false)),
		@AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, nullable = false, length = 45, unique = false)) })
public class Unit extends NamedPersistenceObject {
	@Transient
	private static final long serialVersionUID = 2437063899438647082L;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Basic
	@Column(name = "size", nullable = false)
	private Long size;

	@Formula("(select sum(p.pallets) / (select u.size from unit u where u.idUnit=idUnit) from product p where p.idProduct in (select up.product_id from unitProduct up where up.unit_id=idUnit))")
	private Float usage;

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

	public synchronized final String getDescription() {
		return description;
	}

	public synchronized final void setDescription(String description) {
		this.description = description;
	}

	public synchronized final Long getSize() {
		return size;
	}

	public synchronized final void setSize(Long size) {
		this.size = size;
	}

	public synchronized final Float getUsage() {
		return usage;
	}

	public synchronized final Warehouse getWarehouse() {
		return warehouse;
	}

	public synchronized final void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public synchronized final UnitType getType() {
		return type;
	}

	public synchronized final void setType(UnitType type) {
		this.type = type;
	}

	public synchronized final Set<Product> getProducts() {
		return products;
	}

	public synchronized final void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unit [");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (size != null)
			builder.append("size=").append(size).append(", ");
		if (usage != null)
			builder.append("usage=").append(usage).append(", ");
		if (warehouse != null)
			builder.append("warehouse=").append(warehouse).append(", ");
		if (type != null)
			builder.append("type=").append(type).append(", ");
		if (products != null)
			builder.append("products=").append(products).append(", ");
		if (super.toString() != null)
			builder.append("toString()=").append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((usage == null) ? 0 : usage.hashCode());
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
		if (!(obj instanceof Unit))
			return false;
		Unit other = (Unit) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (usage == null) {
			if (other.usage != null)
				return false;
		} else if (!usage.equals(other.usage))
			return false;
		if (warehouse == null) {
			if (other.warehouse != null)
				return false;
		} else if (!warehouse.equals(other.warehouse))
			return false;
		return true;
	}

}
