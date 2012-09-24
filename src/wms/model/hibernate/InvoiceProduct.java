package wms.model.hibernate;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "invoiceProduct", schema = "majekwms")
@AssociationOverrides({
		@AssociationOverride(name = "invoice", joinColumns = @JoinColumn(name = "idNumber")),
		@AssociationOverride(name = "product", joinColumns = @JoinColumn(name = "idNumber")) })
public class InvoiceProduct implements Serializable {
	@Transient
	private static final long serialVersionUID = 1269575448414133565L;

	@EmbeddedId
	private InvoiceProductId pk = new InvoiceProductId();

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

	public final InvoiceProductId getPk() {
		return pk;
	}

	@Transient
	public final Double getQuantity() {
		return quantity;
	}

	@Transient
	public final Float getPrice() {
		return price;
	}

	@Transient
	public final Integer getTax() {
		return tax;
	}

	@Transient
	public final String getComment() {
		return comment;
	}

	public final void setPk(InvoiceProductId pk) {
		this.pk = pk;
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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		InvoiceProduct that = (InvoiceProduct) o;

		if (this.getPk() != null ? !this.getPk().equals(that.getPk()) : that
				.getPk() != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (this.getPk() != null ? this.getPk().hashCode() : 0);
	}
}
