package wms.model.client;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import wms.model.basic.PersistenceObject;

@Entity
@Table(name = "address")
@AttributeOverride(name = "id", column = @Column(name = "idAddress", updatable = false, insertable = true, nullable = false))
public class Address extends PersistenceObject {
	private static final long serialVersionUID = -252544167932088793L;

	@Basic
	@Column(name = "street", nullable = false, unique = true, length = 45, updatable = true)
	private String street;

	@Basic
	@Column(name = "postcode", nullable = false, unique = true, length = 12, updatable = true)
	private String postcode;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", referencedColumnName = "idCity", insertable = true, updatable = true)
	private City city;

	public Address() {
		super();
	}

	public synchronized final String getStreet() {
		return street;
	}

	public synchronized final void setStreet(String street) {
		this.street = street;
	}

	public synchronized final String getPostcode() {
		return postcode;
	}

	public synchronized final void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public synchronized final City getCity() {
		return city;
	}

	public synchronized final void setCity(City city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (postcode == null) {
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", postcode=" + postcode
				+ ", city=" + city + ", toString()=" + super.toString() + "]";
	}

}
