package wms.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Invoice extends AbstractEntity {
	private static final long serialVersionUID = -3204092137188652431L;

	private String invoiceNumber;
	private Date createdDate;
	private Date dueDate;
	private String description;
	private Client client;
	private Set<Product> products;

	public Invoice() {
		super(); // hibernate
		this.products = new HashSet<>();
	}

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description, Client client) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.client = client;
		this.products = new HashSet<>();
	}

	public Invoice(String invoiceNumber, Date createdDate, Date dueDate,
			String description) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.description = description;
		this.products = new HashSet<>();
	}

	/**
	 * @return the invoiceNumber
	 */
	public final String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @return the createdDate
	 */
	public final Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the dueDate
	 */
	public final Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return the client
	 */
	public final Client getClient() {
		return client;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public final void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public final void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public final void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
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
