package wms.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		name = "warehouse", 
		uniqueConstraints = { 
				@UniqueConstraint(columnNames = { "name" }) 
		})
@DiscriminatorValue("Warehouse")
@AttributeOverride (name = "idWarehouse", column = @Column(name = "idNumber"))
public class Warehouse extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Unit> units = new HashSet<>();

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, Date createdDate) {
		super(name, description, size, maxSize);
		this.createdDate = createdDate;
	}

	public Warehouse(String name, String description, Integer size,
			Integer maxSize, Date createdDate, Set<Unit> setOfUnits) {
		super(name, description, size, maxSize);
		this.setCreatedDate(createdDate);
		this.setUnits(setOfUnits);
	}

	public Date getCreatedDate() {
		return createdDate;
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
			return this.name.equals(that.name);
		}

		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * this.createdDate.hashCode() * 13;
	}
}
