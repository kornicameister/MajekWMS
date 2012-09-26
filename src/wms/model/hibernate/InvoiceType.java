package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "invoiceType", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("InvoiceType")
public class InvoiceType extends AbstractEntity {
	private static final long serialVersionUID = -7345851338532573657L;

	@Id
	@Column(name = "idInvoiceType", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idInvoiceType;

	@Basic
	@Column(name = "name", length = 10, insertable = true, updatable = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceType")
	private Set<Invoice> invoices = new HashSet<>();

	public InvoiceType() {
		super();
	}

	public InvoiceType(Integer idInvoiceType, String name, Set<Invoice> invoices) {
		super();
		this.idInvoiceType = idInvoiceType;
		this.name = name;
		this.invoices = invoices;
	}

	public final Integer getIdInvoiceType() {
		return idInvoiceType;
	}

	public final String getName() {
		return name;
	}

	public final Set<Invoice> getInvoices() {
		return invoices;
	}

	public final void setIdInvoiceType(Integer idInvoiceType) {
		this.idInvoiceType = idInvoiceType;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((idInvoiceType == null) ? 0 : idInvoiceType.hashCode());
		result = prime * result
				+ ((invoices == null) ? 0 : invoices.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceType other = (InvoiceType) obj;
		if (idInvoiceType == null) {
			if (other.idInvoiceType != null)
				return false;
		} else if (!idInvoiceType.equals(other.idInvoiceType))
			return false;
		if (invoices == null) {
			if (other.invoices != null)
				return false;
		} else if (!invoices.equals(other.invoices))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
