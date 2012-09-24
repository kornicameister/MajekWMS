package wms.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "invoice", schema = "majekwms", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Invoice extends AbstractEntity {
	private static final long serialVersionUID = -3204092137188652431L;

	private String invoiceNumber;
	private Date createdDate;
	private Date dueDate;
	private String description;
	private Client client;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>();

	public Invoice() {
		super(); // hibernate
	}

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
	}

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description, Client client) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.client = client;
	}

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description, Client client,
			Set<InvoiceProduct> invoiceProducts) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.client = client;
		this.invoiceProducts = invoiceProducts;
	}

	public final String getInvoiceNumber() {
		return invoiceNumber;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final Date getDueDate() {
		return dueDate;
	}

	public final Client getClient() {
		return client;
	}

	public final String getDescription() {
		return description;
	}

	public final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public final void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public final void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public final void setClient(Client client) {
		this.client = client;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	@Override
	public boolean equals(Object obj) {
		boolean invoiceNumberCmpr = this.invoiceNumber.equals(((Invoice) obj)
				.getInvoiceNumber());
		if (!invoiceNumberCmpr) {
			return super.equals(obj);
		}
		return invoiceNumberCmpr;
	}

}
