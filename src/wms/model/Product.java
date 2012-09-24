package wms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "unit", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Product extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 1246737308278979025L;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	private String name;

	@Column(name = "description", nullable = true, length = 250)
	private String description;

	@Column(name = "quantity", nullable = false, insertable = true, updatable = true)
	private Double quantity;

	@Column(name = "price", nullable = false, insertable = true, updatable = true)
	private Double price;

	@Column(name = "tax", nullable = false, insertable = true, updatable = true)
	private Float tax;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	@PrimaryKeyJoinColumn(name = "vendor", referencedColumnName = "idNumber")
	private Client vendor;

	@Column(name = "vendor", unique = false, updatable = true, insertable = false)
	private Integer idVendor;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	@PrimaryKeyJoinColumn(name = "measure", referencedColumnName = "idNumber")
	private Measure measure;

	@Column(name = "measure", unique = false, updatable = true, insertable = true, nullable = false)
	private Integer idMeasure;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>();

	public Product() {
		super(); // hibernate
	}

	public Product(String name, String description, Double quantity,
			Double price, Float tax) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
	}

	public Product(String name, String description, Double quantity,
			Double price, Float tax, Client vendor) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.setVendor(vendor);
	}

	public Product(String name, String description, Double quantity,
			Double price, Float tax, Client vendor, Measure measure) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.setVendor(vendor);
		this.setMeasure(measure);
	}
	
	public Product(String name, String description, Double quantity,
			Double price, Float tax, Client vendor, Measure measure, Set<InvoiceProduct> invoiceProducts) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.setVendor(vendor);
		this.setMeasure(measure);
		this.setInvoiceProducts(invoiceProducts);
	}

	public final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getPrice() {
		return price;
	}

	public Float getTax() {
		return tax;
	}

	public Client getVendor() {
		return vendor;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}
	
	public final void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public void setVendor(Client vendor) {
		this.vendor = vendor;
		this.idVendor = vendor.getId();
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
		this.idMeasure = measure.getId();
	}
}
