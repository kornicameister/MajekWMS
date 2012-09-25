package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "product", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name" }),
		@UniqueConstraint(columnNames = { "idNumber" }) })
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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = "vendor", referencedColumnName = "idNumber")
	private Client vendor;

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "measure"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idMeasure", unique = true, nullable = false)
	private Integer measureId;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "idMeasure", referencedColumnName = "idNumber")
	private Measure measure;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>(0);

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
	private Set<Unit> units = new HashSet<>(0);
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
	private Set<Client> client = new HashSet<>(0);

	public Product() {
		super();
	}

	public Product(String name, String description, Double quantity,
			Double price, Float tax, Client vendor, Measure measure,
			Set<InvoiceProduct> invoiceProducts, Set<Unit> units) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.vendor = vendor;
		this.measure = measure;
		this.invoiceProducts = invoiceProducts;
		this.units = units;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final Double getQuantity() {
		return quantity;
	}

	public final Double getPrice() {
		return price;
	}

	public final Float getTax() {
		return tax;
	}

	public final Client getVendor() {
		return vendor;
	}

	public final Integer getMeasureId() {
		return measureId;
	}

	public final Measure getMeasure() {
		return measure;
	}

	public final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public final Set<Unit> getUnits() {
		return units;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public final void setPrice(Double price) {
		this.price = price;
	}

	public final void setTax(Float tax) {
		this.tax = tax;
	}

	public final void setVendor(Client vendor) {
		this.vendor = vendor;
	}

	public final void setMeasureId(Integer measureId) {
		this.measureId = measureId;
	}

	public final void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public final void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public final void setUnits(Set<Unit> units) {
		this.units = units;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = super.equals(o);
		Product that = (Product) o;

		if (result) {
			result = this.vendor.equals(that.vendor);
		}
		if (result) {
			result = this.name.equals(that.name);
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();

		hash = hash * this.name.hashCode();
		hash = hash * this.invoiceProducts.hashCode();
		hash = hash * this.units.hashCode();

		return hash;
	}
}
