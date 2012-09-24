package wms.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InvoiceProductId implements Serializable {
	private static final long serialVersionUID = -614537106939514217L;

	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private Product product;

	public final Invoice getInvoice() {
		return invoice;
	}

	public final Product getProduct() {
		return product;
	}

	public final void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public final void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		}

		InvoiceProductId that = (InvoiceProductId) o;
		if (this.invoice != null ? !this.invoice.equals(that.invoice)
				: that.invoice != null) {
			return false;
		} else if (this.product != null ? !this.product.equals(that.product)
				: that.product != null) {
			return false;
		}

		return true;
	}
	
	@Override
	public int hashCode() {
        int result;
        result = (this.product != null ? this.product.hashCode() : 0);
        result = 31 * result + (this.invoice != null ? this.invoice.hashCode() : 0);
        return result;
    }

}
