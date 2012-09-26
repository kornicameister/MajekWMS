package wms.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("Warehouse")
public class Warehouse extends AbstractStorageUnit {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Id
	@Column(name = "idWarehouse", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idWarehouse;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Unit> units = new HashSet<>();

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(String name, String description, Integer size,
			Integer maximumSize) {
		super(name, description, size, maximumSize);
	}

	public Warehouse(String name, String description, Integer size,
			Integer maximumSize, Integer idWarehouse, Set<Unit> units,
			Date createdDate) {
		super(name, description, size, maximumSize);
		this.idWarehouse = idWarehouse;
		this.units = units;
		this.createdDate = createdDate;
	}

	public final Integer getIdWarehouse() {
		return idWarehouse;
	}

	public final Set<Unit> getUnits() {
		return units;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setIdWarehouse(Integer idWarehouse) {
		this.idWarehouse = idWarehouse;
	}

	public final void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((idWarehouse == null) ? 0 : idWarehouse.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Warehouse))
			return false;
		Warehouse other = (Warehouse) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (idWarehouse == null) {
			if (other.idWarehouse != null)
				return false;
		} else if (!idWarehouse.equals(other.idWarehouse))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true && super.equals(obj);
	}

}
