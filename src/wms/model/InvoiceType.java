package wms.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
@AttributeOverrides(value = {
		@AttributeOverride(name = "id", column = @Column(name = "idInvoiceType", updatable = false, insertable = true, nullable = false)),
		@AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = false, nullable = false, length = 10, unique = true)) })
public class InvoiceType extends NamedPersistenceObject {
	@Transient
	private static final long serialVersionUID = -7345851338532573657L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idInvoiceType", referencedColumnName = "invoicetype_id", insertable = false, updatable = false)
	private Invoice invoice;

	public InvoiceType() {
		super();
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
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceType [");
		if (invoice != null)
			builder.append("invoice=").append(invoice).append(", ");
		if (super.toString() != null)
			builder.append("toString()=").append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
