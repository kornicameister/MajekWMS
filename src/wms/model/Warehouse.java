package wms.model;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Warehouse extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Id
	@Column(name = "idWarehouse", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer id;

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	protected String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Basic
	@Column(name = "size", nullable = true)
	private Integer size;

	@Basic
	@Column(name = "maxSize", nullable = false)
	private Integer maximumSize;

	@OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Unit> units = new HashSet<>();

	public Warehouse() {
		super(); // for hibernate
	}

	public Warehouse(Integer idWarehouse, Date createdDate, String name,
			String description, Integer size, Integer maximumSize) {
		super();
		this.id = idWarehouse;
		this.createdDate = createdDate;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public Warehouse(Integer idWarehouse, Set<Unit> units,
			Date createdDate, String name, String description, Integer size,
			Integer maximumSize) {
		super();
		this.id = idWarehouse;
		this.units = units;
		this.createdDate = createdDate;
		this.name = name;
		this.description = description;
		this.size = size;
		this.maximumSize = maximumSize;
	}

	public final Integer getIdWarehouse() {
		return id;
	}

	public final Set<Unit> getUnits() {
		return units;
	}

	public final Date getCreatedDate() {
		return createdDate;
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

	public final void setIdWarehouse(Integer idWarehouse) {
		this.id = idWarehouse;
	}

	public final void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
