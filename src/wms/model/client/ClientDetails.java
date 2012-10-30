package wms.model.client;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import wms.model.BaseEntity;

@Entity
@Table(name = "clientDetails")
public class ClientDetails extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -4305913399009774547L;

	@Id
	@Column(name = "idClientDetails", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "client"))
	private Long id;

	@Basic
	@Column(name = "phone", length = 20, insertable = true, updatable = true, nullable = false)
	private String phone;

	@Basic
	@Column(name = "fax", length = 11, insertable = true, updatable = true, nullable = false)
	private String fax;

	@Basic
	@Column(name = "account", length = 30, insertable = true, updatable = true, nullable = false)
	private String account;

	@Basic
	@Column(name = "nip", length = 15, insertable = true, updatable = true, nullable = false)
	private String nip;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "idAddress")
	private Address address;

	public ClientDetails() {
		super();
	}

	public synchronized final Long getId() {
		return id;
	}

	public synchronized final void setId(Long id) {
		this.id = id;
	}

	public synchronized final String getPhone() {
		return phone;
	}

	public synchronized final void setPhone(String phone) {
		this.phone = phone;
	}

	public synchronized final String getFax() {
		return fax;
	}

	public synchronized final void setFax(String fax) {
		this.fax = fax;
	}

	public synchronized final String getAccount() {
		return account;
	}

	public synchronized final void setAccount(String account) {
		this.account = account;
	}

	public synchronized final String getNip() {
		return nip;
	}

	public synchronized final void setNip(String nip) {
		this.nip = nip;
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
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((nip == null) ? 0 : nip.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ClientDetails))
			return false;
		ClientDetails other = (ClientDetails) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (nip == null) {
			if (other.nip != null)
				return false;
		} else if (!nip.equals(other.nip))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientDetails [phone=" + phone + ", fax=" + fax + ", account="
				+ account + ", nip=" + nip + ", address=" + address + "]";
	}

}
