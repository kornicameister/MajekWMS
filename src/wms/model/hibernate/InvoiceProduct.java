package wms.model.hibernate;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "invoiceProduct", schema = "majekwms")
@AssociationOverrides({
		@AssociationOverride(name = "idInvoice", joinColumns = @JoinColumn(name = "fkInvoice")),
		@AssociationOverride(name = "idProduct", joinColumns = @JoinColumn(name = "fkProduct")) })
public class InvoiceProduct implements Serializable {
	@Transient
	private static final long serialVersionUID = 1269575448414133565L;

	@EmbeddedId
	private InvoiceProductIdentifier invoiceProductFK = new InvoiceProductIdentifier();

	@Id
	@Column(name = "idInvoiceProduct", updatable = false, insertable = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "assigned", strategy = "assigned")
	private Integer id;

	@Basic
	@Column(name = "quantity", insertable = true, nullable = false, updatable = true)
	private Double quantity;

	@Basic
	@Column(name = "price", insertable = true, nullable = false, updatable = true)
	private Float price;

	@Basic
	@Column(name = "tax", insertable = true, nullable = false, updatable = true)
	private Integer tax;

	@Basic
	@Column(name = "comment", insertable = true, nullable = false, updatable = true, length = 45)
	private String comment;

	public InvoiceProduct() {
		super(); // hibernate
	}

	public InvoiceProduct(Integer id, Double quantity, Float price,
			Integer tax, String comment) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public final InvoiceProductIdentifier getPk() {
		return invoiceProductFK;
	}

	public Invoice getInvoiceEntity() {
		return this.getPk().getInvoice();
	}

	public Product getProductEntity() {
		return this.getPk().getProduct();
	}

	public final Double getQuantity() {
		return quantity;
	}

	public final Float getPrice() {
		return price;
	}

	public final Integer getTax() {
		return tax;
	}

	public final String getComment() {
		return comment;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public final void setPk(InvoiceProductIdentifier pk) {
		this.invoiceProductFK = pk;
	}

	public final void setProductEntity(Product product) {
		this.getPk().setProduct(product);
	}

	public final void setInvoiceEntity(Invoice invoice) {
		this.getPk().setInvoice(invoice);
	}

	public final void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public final void setPrice(Float price) {
		this.price = price;
	}

	public final void setTax(Integer tax) {
		this.tax = tax;
	}

	public final void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime
				* result
				+ ((invoiceProductFK == null) ? 0 : invoiceProductFK.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceProduct other = (InvoiceProduct) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (invoiceProductFK == null) {
			if (other.invoiceProductFK != null)
				return false;
		} else if (!invoiceProductFK.equals(other.invoiceProductFK))
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
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceProduct [");
		if (invoiceProductFK != null) {
			builder.append("pk=");
			builder.append(invoiceProductFK);
			builder.append(", ");
		}
		if (quantity != null) {
			builder.append("quantity=");
			builder.append(quantity);
			builder.append(", ");
		}
		if (price != null) {
			builder.append("price=");
			builder.append(price);
			builder.append(", ");
		}
		if (tax != null) {
			builder.append("tax=");
			builder.append(tax);
			builder.append(", ");
		}
		if (comment != null) {
			builder.append("comment=");
			builder.append(comment);
		}
		builder.append("]");
		return builder.toString();
	}
}
