package wms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "invoiceType", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class InvoiceType extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -7345851338532573657L;

	@Id
	@Column(name = "idInvoiceType", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idInvoiceType;

	@Basic
	@Column(name = "name", length = 10, insertable = true, updatable = true)
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idInvoiceType", referencedColumnName = "fkInvoiceType", insertable = false, updatable = false)
	private Invoice masterInvoice;

	public InvoiceType() {
		super();
	}

	public InvoiceType(Integer idInvoiceType, String name,
			Invoice masterInvoice) {
		super();
		this.idInvoiceType = idInvoiceType;
		this.name = name;
		this.masterInvoice = masterInvoice;
	}

	public final Integer getIdInvoiceType() {
		return idInvoiceType;
	}

	public final String getName() {
		return name;
	}

	public final Invoice getMasterInvoice() {
		return masterInvoice;
	}

	public final void setIdInvoiceType(Integer idInvoiceType) {
		this.idInvoiceType = idInvoiceType;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setMasterInvoice(Invoice masterInvoice) {
		this.masterInvoice = masterInvoice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((idInvoiceType == null) ? 0 : idInvoiceType.hashCode());
		result = prime * result
				+ ((masterInvoice == null) ? 0 : masterInvoice.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof InvoiceType))
			return false;
		InvoiceType other = (InvoiceType) obj;
		if (idInvoiceType == null) {
			if (other.idInvoiceType != null)
				return false;
		} else if (!idInvoiceType.equals(other.idInvoiceType))
			return false;
		if (masterInvoice == null) {
			if (other.masterInvoice != null)
				return false;
		} else if (!masterInvoice.equals(other.masterInvoice))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceType [");
		if (getIdInvoiceType() != null) {
			builder.append("getIdInvoiceType()=");
			builder.append(getIdInvoiceType());
			builder.append(", ");
		}
		if (getName() != null) {
			builder.append("getName()=");
			builder.append(getName());
			builder.append(", ");
		}
		if (getMasterInvoice() != null) {
			builder.append("getMasterInvoice()=");
			builder.append(getMasterInvoice());
			builder.append(", ");
		}
		builder.append("getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
