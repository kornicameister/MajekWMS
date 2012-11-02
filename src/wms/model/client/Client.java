package wms.model.client;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import wms.model.basic.NamedPersistenceObject;

@Entity
@Table(name = "client", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@AttributeOverrides(value = {
		@AttributeOverride(name = "id", column = @Column(name = "idClient", updatable = false, insertable = true, nullable = false)),
		@AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, nullable = false, length = 45, unique = true)) })
public class Client extends NamedPersistenceObject {
	@Transient
	private static final long serialVersionUID = 1283426340575080285L;

	@Basic
	@Column(name = "company", insertable = true, nullable = false, unique = true, length = 45, updatable = true)
	private String company;

	@Basic
	@Column(name = "description", nullable = true, length = 200)
	private String description;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = "details_id", referencedColumnName = "idClientDetails")
	private ClientDetails details;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "idAddress")
	private Address address;

	public Client() {
		super();
	}

	public synchronized final String getCompany() {
		return company;
	}

	public synchronized final void setCompany(String company) {
		this.company = company;
	}

	public synchronized final String getDescription() {
		return description;
	}

	public synchronized final void setDescription(String description) {
		this.description = description;
	}

	public synchronized final ClientDetails getDetails() {
		return details;
	}

	public synchronized final void setDetails(ClientDetails details) {
		this.details = details;
	}

	public synchronized final Address getAddress() {
		return address;
	}

	public synchronized final void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Client))
			return false;
		Client other = (Client) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [");
		if (company != null)
			builder.append("company=").append(company).append(", ");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (details != null)
			builder.append("details=").append(details).append(", ");
		if (address != null)
			builder.append("address=").append(address).append(", ");
		if (super.toString() != null)
			builder.append("toString()=").append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
