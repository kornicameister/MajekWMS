package wms.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;

import wms.model.basic.NamedPersistenceObject;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@AttributeOverride(name = "id", column = @Column(name = "idWarehouse", updatable = false, insertable = true, nullable = false))
public class Warehouse extends NamedPersistenceObject {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

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

	public synchronized final Date getCreatedDate() {
		return createdDate;
	}

	public synchronized final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Warehouse [");
		if (createdDate != null)
			builder.append("createdDate=").append(createdDate).append(", ");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (usage != null)
			builder.append("usage=").append(usage).append(", ");
		if (size != null)
			builder.append("size=").append(size).append(", ");
		if (super.toString() != null)
			builder.append("toString()=").append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
