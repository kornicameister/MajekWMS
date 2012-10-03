package wms.model;

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
@Table(name = "invoice", schema = "majekwms", uniqueConstraints = { @UniqueConstraint(columnNames = { "refNumber" }) })
public class Invoice extends BaseEntity {
	@Transient
	private static final long serialVersionUID = -3204092137188652431L;

	@Id
	@Column(name = "idInvoice", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer id;

	@Column(name = "refNumber", updatable = false, insertable = true, nullable = false)
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

	@OneToOne(mappedBy = "invoice", fetch = FetchType.LAZY)
	private InvoiceType invoiceType;

	public Invoice() {
		super(); // hibernate
	}

	public Invoice(Integer idInvoice, String invoiceNumber, Date createdDate,
			Date dueDate, String description) {
		super();
		this.id = idInvoice;
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
	}

	public Invoice(Integer idInvoice, String invoiceNumber, Date createdDate,
			Date dueDate, String description, Client invoiceClient,
			Set<InvoiceProduct> invoiceProducts, InvoiceType invoiceType) {
		super();
		this.id = idInvoice;
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.invoiceClient = invoiceClient;
		this.invoiceProducts = invoiceProducts;
		this.invoiceType = invoiceType;
	}

	public final Integer getIdInvoice() {
		return id;
	}

	public final void setIdInvoice(Integer idInvoice) {
		this.id = idInvoice;
	}

	public final String getInvoiceNumber() {
		return invoiceNumber;
	}

	public final void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public final Date getDueDate() {
		return dueDate;
	}

	public final void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Client getInvoiceClient() {
		return invoiceClient;
	}

	public final void setInvoiceClient(Client invoiceClient) {
		this.invoiceClient = invoiceClient;
	}

	public final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public final void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public final InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public final void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((invoiceClient == null) ? 0 : invoiceClient.hashCode());
		result = prime * result
				+ ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime * result
				+ ((invoiceProducts == null) ? 0 : invoiceProducts.hashCode());
		result = prime * result
				+ ((invoiceType == null) ? 0 : invoiceType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Invoice))
			return false;
		Invoice other = (Invoice) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceClient == null) {
			if (other.invoiceClient != null)
				return false;
		} else if (!invoiceClient.equals(other.invoiceClient))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (invoiceProducts == null) {
			if (other.invoiceProducts != null)
				return false;
		} else if (!invoiceProducts.equals(other.invoiceProducts))
			return false;
		if (invoiceType == null) {
			if (other.invoiceType != null)
				return false;
		} else if (!invoiceType.equals(other.invoiceType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice [");
		if (getIdInvoice() != null) {
			builder.append("getIdInvoice()=");
			builder.append(getIdInvoice());
			builder.append(", ");
		}
		if (getInvoiceNumber() != null) {
			builder.append("getInvoiceNumber()=");
			builder.append(getInvoiceNumber());
			builder.append(", ");
		}
		if (getCreatedDate() != null) {
			builder.append("getCreatedDate()=");
			builder.append(getCreatedDate());
			builder.append(", ");
		}
		if (getDueDate() != null) {
			builder.append("getDueDate()=");
			builder.append(getDueDate());
			builder.append(", ");
		}
		if (getDescription() != null) {
			builder.append("getDescription()=");
			builder.append(getDescription());
			builder.append(", ");
		}
		if (getInvoiceClient() != null) {
			builder.append("getInvoiceClient()=");
			builder.append(getInvoiceClient());
			builder.append(", ");
		}
		if (getInvoiceProducts() != null) {
			builder.append("getInvoiceProducts()=");
			builder.append(getInvoiceProducts());
			builder.append(", ");
		}
		if (getInvoiceType() != null) {
			builder.append("getInvoiceType()=");
			builder.append(getInvoiceType());
			builder.append(", ");
		}
		builder.append("hashCode()=");
		builder.append(hashCode());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
