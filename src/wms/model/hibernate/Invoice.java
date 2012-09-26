package wms.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "invoice", uniqueConstraints = { @UniqueConstraint(columnNames = { "invoiceNumber" }) })
@DiscriminatorValue("Invoice")
@AttributeOverride (name = "idInvoice", column = @Column(name = "idNumber"))
public class Invoice extends AbstractEntity {
	private static final long serialVersionUID = -3204092137188652431L;

	@Id
	@Column(name = "invoiceNumber", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "assigned", strategy = "assigned")
	private String invoiceNumber;

	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@Column(name = "dueDate", nullable = false)
	private Date dueDate;

	@Column(name = "description", nullable = true, updatable = true, insertable = false, length = 150)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idClient", nullable = false)
	private Client client;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idInvoiceType", nullable = false)
	private InvoiceType invoiceType;

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

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description, Client client,
			Set<InvoiceProduct> invoiceProducts, InvoiceType invoiceType) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.client = client;
		this.invoiceProducts = invoiceProducts;
		this.invoiceType = invoiceType;
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

	public final String getDescription() {
		return description;
	}

	public final Client getClient() {
		return client;
	}

	public final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public final InvoiceType getInvoiceType() {
		return invoiceType;
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

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setClient(Client client) {
		this.client = client;
	}

	public final void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public final void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
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
