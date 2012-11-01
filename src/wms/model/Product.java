package wms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "product")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@AttributeOverride(name = "id", column = @Column(name = "idProduct", updatable = false, insertable = true, nullable = false))
public class Product extends BaseEntity {
	@Transient
	private static final long serialVersionUID = 1246737308278979025L;

	@Basic
	@Column(name = "name", nullable = false, unique = true, length = 20, updatable = true)
	private String name;

	@Basic
	@Column(name = "description", nullable = true, length = 250)
	private String description;

	@Basic
	@Column(name = "pallets", nullable = false, insertable = true, updatable = true)
	private Integer pallets;

	@Basic
	@Column(name = "quantity", nullable = false, insertable = true, updatable = true)
	private Double quantity;

	@Basic
	@Column(name = "price", nullable = false, insertable = true, updatable = true)
	private Double price;

	@Basic
	@Column(name = "tax", nullable = false, insertable = true, updatable = true)
	private Float tax;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product")
	private Set<InvoiceProduct> invoiceProducts = new HashSet<>(0);

	@JoinColumn(name = "measure_id", referencedColumnName = "idMeasure")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Measure measure;

	public Product() {
		super();
	}

	public synchronized final String getName() {
		return name;
	}

	public synchronized final void setName(String name) {
		this.name = name;
	}

	public synchronized final String getDescription() {
		return description;
	}

	public synchronized final void setDescription(String description) {
		this.description = description;
	}

	public synchronized final Double getQuantity() {
		return quantity;
	}

	public synchronized final void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public synchronized final Double getPrice() {
		return price;
	}

	public synchronized final void setPrice(Double price) {
		this.price = price;
	}

	public synchronized final Float getTax() {
		return tax;
	}

	public synchronized final void setTax(Float tax) {
		this.tax = tax;
	}

	public synchronized final Set<InvoiceProduct> getInvoiceProducts() {
		return invoiceProducts;
	}

	public synchronized final void setInvoiceProducts(
			Set<InvoiceProduct> invoiceProducts) {
		this.invoiceProducts = invoiceProducts;
	}

	public synchronized final Measure getMeasure() {
		return measure;
	}

	public synchronized final void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public synchronized final Integer getPallets() {
		return pallets;
	}

	public synchronized final void setPallets(Integer pallets) {
		this.pallets = pallets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((invoiceProducts == null) ? 0 : invoiceProducts.hashCode());
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pallets == null) ? 0 : pallets.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (invoiceProducts == null) {
			if (other.invoiceProducts != null)
				return false;
		} else if (!invoiceProducts.equals(other.invoiceProducts))
			return false;
		if (measure == null) {
			if (other.measure != null)
				return false;
		} else if (!measure.equals(other.measure))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pallets == null) {
			if (other.pallets != null)
				return false;
		} else if (!pallets.equals(other.pallets))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
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
		return true;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description
				+ ", pallets=" + pallets + ", quantity=" + quantity
				+ ", price=" + price + ", tax=" + tax + ", invoiceProducts="
				+ invoiceProducts + ", measure=" + measure + ", toString()="
				+ super.toString() + "]";
	}

}
