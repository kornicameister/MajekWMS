package wms.model.hibernate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InvoiceProductIdentifier implements Serializable {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof InvoiceProductIdentifier))
			return false;
		InvoiceProductIdentifier other = (InvoiceProductIdentifier) obj;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}


}