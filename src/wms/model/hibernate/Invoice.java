package wms.model.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
		name = "invoice",
		schema = "majekwms",
		uniqueConstraints = { 
				@UniqueConstraint(columnNames = { "invoiceNumber" }) 
				}
		)
public class Invoice extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -3204092137188652431L;

	@Id
	@Column(name = "invoiceNumber", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "assigned", strategy = "assigned")
	private String invoiceNumber;

	@Basic
	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@Basic
	@Column(name = "dueDate", nullable = false)
	private Date dueDate;

	@Basic
	@Column(name = "description", nullable = true, updatable = true, insertable = false, length = 150)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fkClient", referencedColumnName = "idClient")
	private Client invoiceClient;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>(0);
	
	@OneToOne(mappedBy = "masterInvoice", fetch = FetchType.LAZY)
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
		this.invoiceClient = client;
	}

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description, Client client,
			Set<InvoiceProduct> invoiceProducts) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.invoiceClient = client;
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
		this.invoiceClient = client;
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
		return invoiceClient;
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
		this.invoiceClient = client;
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
