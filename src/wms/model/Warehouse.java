package wms.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;

import wms.controller.base.annotations.HideAssociation;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "warehouse", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Warehouse extends BasicPersistanceObject {
	@Transient
	private static final long serialVersionUID = 4557522901223374020L;

	@Id
	@Column(name = "idWarehouse", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "company"))
	@SerializedName(value = "id")
	private Long companyId;

	@Basic
	@Column(name = "name", length = 20, unique = true, updatable = true, nullable = false)
	@NaturalId
	private String name;

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

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@HideAssociation
	private Company company;

	public Warehouse() {
		super();
	}

	public synchronized final Long getCompanyId() {
		return companyId;
	}

	public synchronized final void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public synchronized final String getName() {
		return name;
	}

	public synchronized final void setName(String name) {
		this.name = name;
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

	public synchronized final Company getCompany() {
		return company;
	}

	public synchronized final void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Warehouse [companyId=" + companyId + ", name=" + name
				+ ", createdDate=" + createdDate + ", description="
				+ description + ", usage=" + usage + ", size=" + size
				+ ", company=" + company + ", toString()=" + super.toString()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
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

}
