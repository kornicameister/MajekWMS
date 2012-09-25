package wms.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Warehouse extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "warehouseUnit", schema = "majekwms", joinColumns = { @JoinColumn(name = "idUnit", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "idWarehouse", nullable = false, updatable = false) })
	private Set<Unit> units = new HashSet<>();

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "type", referencedColumnName = "idNumber")
	private WarehouseType warehouseType;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, WarehouseType type, Date createdDate) {
		super(name, description, size, maxSize);
		this.setType(type);
		this.createdDate = createdDate;
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, WarehouseType type, Date createdDate,
			Set<Unit> setOfUnits) {
		super(name, description, size, maxSize);
		this.setType(type);
		this.setCreatedDate(createdDate);
		this.setUnits(setOfUnits);
	}

	public Integer getType() {
		return warehouseType.getId();
	}

	public WarehouseType getWarehouseType() {
		return this.warehouseType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setType(WarehouseType type) {
		this.warehouseType = type;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Set<Unit> getUnits() {
		return units;
	}

	public void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public void addUnit(Unit unit) {
		this.units.add(unit);
	}

	@Override
	public boolean equals(Object o) {
		boolean result = super.equals(o);
		Warehouse that = (Warehouse) o;

		if (result) {
			return this.name.equals(that.name)
					&& this.warehouseType.equals(that.warehouseType);
		}

		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * this.createdDate.hashCode() * (this.warehouseType == null ? 1 : this.warehouseType.hashCode());
	}
}
