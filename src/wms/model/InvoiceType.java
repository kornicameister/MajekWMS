package wms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "invoiceType", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@AttributeOverride(name = "id", column = @Column(name = "idInvoiceType", updatable = false, insertable = true, nullable = false))
public class InvoiceType extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -7345851338532573657L;

	@Basic
	@Column(name = "name", length = 10, insertable = true, updatable = true)
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idInvoiceType", referencedColumnName = "invoicetype_id", insertable = false, updatable = false)
	private Invoice invoice;

	public InvoiceType() {
		super();
	}

	public synchronized final String getName() {
		return name;
	}

	public synchronized final void setName(String name) {
		this.name = name;
	}

	public synchronized final Invoice getInvoice() {
		return invoice;
	}

	public synchronized final void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
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
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
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
		return "InvoiceType [name=" + name + ", invoice=" + invoice + "]";
	}

}
