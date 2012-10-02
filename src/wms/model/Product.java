package wms.model;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {
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

	@Basic
	@Column(name = "description", nullable = true, length = 250)
	private String description;

	@Basic
	@Column(name = "quantity", nullable = false, insertable = true, updatable = true)
	private Double quantity;

	@Basic
	@Column(name = "price", nullable = false, insertable = true, updatable = true)
	private Double price;

	@Basic
	@Column(name = "tax", nullable = false, insertable = true, updatable = true)
	private Float tax;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>(0);

	@ManyToMany(mappedBy = "clientsProducts")
	private Set<Client> client = new HashSet<>(0);

	@ManyToMany(mappedBy = "unitsProducts")
	private Set<Unit> units = new HashSet<>(0);

	@JoinColumn(name = "fkMeasure", referencedColumnName = "idMeasure")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Measure productMeasure;

	public Product() {
		super();
	}

	public Product(Integer idProduct, String name, String description,
			Double quantity, Double price, Float tax) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
	}

	public Product(Integer idProduct, String name, String description,
			Double quantity, Double price, Float tax,
			Set<InvoiceProduct> invoiceProducts) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.invoiceProducts = invoiceProducts;
	}

	public Product(Integer idProduct, String name, String description,
			Double quantity, Double price, Float tax,
			Set<InvoiceProduct> invoiceProducts, Set<Client> client) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.invoiceProducts = invoiceProducts;
		this.client = client;
	}

	public Product(Integer idProduct, String name, String description,
			Double quantity, Double price, Float tax,
			Set<InvoiceProduct> invoiceProducts, Set<Client> client,
			Set<Unit> units) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.invoiceProducts = invoiceProducts;
		this.client = client;
		this.units = units;
	}

	public Product(Integer idProduct, String name, String description,
			Double quantity, Double price, Float tax,
			Set<InvoiceProduct> invoiceProducts, Set<Client> client,
			Set<Unit> units, Measure productMeasure) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.invoiceProducts = invoiceProducts;
		this.client = client;
		this.units = units;
		this.productMeasure = productMeasure;
	}

	public final Integer getIdProduct() {
		return idProduct;
	}

	public final void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Double getQuantity() {
		return quantity;
	}

	public final void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public final Double getPrice() {
		return price;
	}

	public final void setPrice(Double price) {
		this.price = price;
	}

	public final Float getTax() {
		return tax;
	}

	public final void setTax(Float tax) {
		this.tax = tax;
	}

	public final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public final void setInvoiceProducts(Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public final Set<Client> getClient() {
		return client;
	}

	public final void setClient(Set<Client> client) {
		this.client = client;
	}

	public final Set<Unit> getUnits() {
		return units;
	}

	public final void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public final Measure getProductMeasure() {
		return productMeasure;
	}

	public final void setProductMeasure(Measure productMeasure) {
		this.productMeasure = productMeasure;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result
				+ ((invoiceProducts == null) ? 0 : invoiceProducts.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((productMeasure == null) ? 0 : productMeasure.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		if (invoiceProducts == null) {
			if (other.invoiceProducts != null)
				return false;
		} else if (!invoiceProducts.equals(other.invoiceProducts))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productMeasure == null) {
			if (other.productMeasure != null)
				return false;
		} else if (!productMeasure.equals(other.productMeasure))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (tax == null) {
			if (other.tax != null)
				return false;
		} else if (!tax.equals(other.tax))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [");
		if (getIdProduct() != null) {
			builder.append("getIdProduct()=");
			builder.append(getIdProduct());
			builder.append(", ");
		}
		if (getName() != null) {
			builder.append("getName()=");
			builder.append(getName());
			builder.append(", ");
		}
		if (getDescription() != null) {
			builder.append("getDescription()=");
			builder.append(getDescription());
			builder.append(", ");
		}
		if (getQuantity() != null) {
			builder.append("getQuantity()=");
			builder.append(getQuantity());
			builder.append(", ");
		}
		if (getPrice() != null) {
			builder.append("getPrice()=");
			builder.append(getPrice());
			builder.append(", ");
		}
		if (getTax() != null) {
			builder.append("getTax()=");
			builder.append(getTax());
			builder.append(", ");
		}
		if (getInvoiceProducts() != null) {
			builder.append("getInvoiceProducts()=");
			builder.append(getInvoiceProducts());
			builder.append(", ");
		}
		if (getClient() != null) {
			builder.append("getClient()=");
			builder.append(getClient());
			builder.append(", ");
		}
		if (getUnits() != null) {
			builder.append("getUnits()=");
			builder.append(getUnits());
			builder.append(", ");
		}
		if (getProductMeasure() != null) {
			builder.append("getProductMeasure()=");
			builder.append(getProductMeasure());
			builder.append(", ");
		}
		builder.append("hashCode()=");
		builder.append(hashCode());
		builder.append(", ");
		if (getUpdatedOn() != null) {
			builder.append("getUpdatedOn()=");
			builder.append(getUpdatedOn());
			builder.append(", ");
		}
		builder.append("getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

}
