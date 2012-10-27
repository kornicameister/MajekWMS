package wms.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;
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
	protected Long id;

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	protected String name;

	@Column(name = "description", nullable = true, length = 666)
	private String description;

	@Formula("(SELECT sum(u.size)/size from unit u where u.warehouse_id = idWarehouse)")
	private Float usage;

	@Basic
	@Column(name = "size", nullable = false)
	private Integer size;

	public Warehouse() {
		super(); // for hibernate
	}

	public synchronized final Long getId() {
		return id;
	}

	public synchronized final void setId(Long id) {
		this.id = id;
	}

	public synchronized final Date getCreatedDate() {
		return createdDate;
	}

	public synchronized final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public synchronized final String getName() {
		return name;
	}

	public synchronized final void setName(String name) {
		this.name = name;
	}

	public synchronized final String getDescription() {
		return description;
	}

	public synchronized final void setDescription(String description) {
		this.description = description;
	}

	public synchronized final Float getUsage() {
		return usage;
	}

	public synchronized final void setUsage(Float usage) {
		this.usage = usage;
	}

	public synchronized final Integer getSize() {
		return size;
	}

	public synchronized final void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((usage == null) ? 0 : usage.hashCode());
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
		if (usage == null) {
			if (other.usage != null)
				return false;
		} else if (!usage.equals(other.usage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", createdDate=" + createdDate
				+ ", name=" + name + ", description=" + description
				+ ", usage=" + usage + ", size=" + size + "]";
	}

}
