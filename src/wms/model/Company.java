package wms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "company", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@AttributeOverride(name = "id", column = @Column(name = "idCompany", updatable = false, insertable = true, nullable = false))
public class Company extends NamedPersistenceObject {
	private static final long serialVersionUID = 7696614972997266832L;

	@Basic
	@NaturalId
	@Column(name = "longname", length = 45, updatable = true, insertable = true, nullable = false)
	private String longName;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "idAddress")
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
	private Warehouse warehouse;

	public Company() {
		super();
	}

	public synchronized final String getLongName() {
		return longName;
	}

	public synchronized final void setLongName(String longName) {
		this.longName = longName;
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
		result = prime * result
				+ ((longName == null) ? 0 : longName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Company))
			return false;
		Company other = (Company) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (longName == null) {
			if (other.longName != null)
				return false;
		} else if (!longName.equals(other.longName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [longName=" + longName + ", address=" + address
				+ ", toString()=" + super.toString() + "]";
	}

}
