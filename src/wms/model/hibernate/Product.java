package wms.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@DiscriminatorValue("Product")
public class Product extends AbstractEntity {
	@Transient
	private static final long serialVersionUID = 1246737308278979025L;

	@Id
	@Column(name = "idProduct", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "increment", strategy = "increment")
	protected Integer idProduct;

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

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "idMeasure"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idMeasure", unique = true, nullable = false)
	private Integer measureId;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "idMeasure", referencedColumnName = "idNumber")
	private Measure measure;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>(0);

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "productsInUnit", targetEntity = Unit.class)
	private Set<Unit> units = new HashSet<>(0);

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "idNumber", targetEntity = Client.class)
	private Set<Client> client = new HashSet<>(0);

	public Product() {
		super();
	}

	public Product(Integer idProduct, String name, String description,
			Double quantity, Double price, Float tax, Integer measureId,
			Measure measure, Set<InvoiceProduct> invoiceProducts,
			Set<Unit> units, Set<Client> client) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.measureId = measureId;
		this.measure = measure;
		this.invoiceProducts = invoiceProducts;
		this.units = units;
		this.client = client;
	}

	public final Integer getIdProduct() {
		return idProduct;
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

	public final Set<Client> getClient() {
		return client;
	}

	public final void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
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

	public final void setClient(Set<Client> client) {
		this.client = client;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = super.equals(o);
		Product that = (Product) o;

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
