package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "invoiceType", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("InvoiceType")
@AttributeOverride (name = "idInvoiceType", column = @Column(name = "idNumber"))
public class InvoiceType extends AbstractEntity {
	private static final long serialVersionUID = -7345851338532573657L;

	@Basic
	@Column(name = "name", length = 10, insertable = true, updatable = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceType")
	private Set<Invoice> invoices = new HashSet<>();

	public InvoiceType() {
		super();
	}

	public InvoiceType(String name) {
		super();
		this.name = name;
	}

	public InvoiceType(String name, Set<Invoice> invoices) {
		super();
		this.name = name;
		this.invoices = invoices;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

}
