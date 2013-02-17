package org.kornicameister.wms.model.embeddable;

import org.kornicameister.wms.model.Invoice;
import org.kornicameister.wms.model.Product;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@Embeddable
public class InvoiceProductPK implements Serializable {
    private static final long serialVersionUID = -614537106939514217L;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Invoice invoice;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
        result = prime * result + ((invoice == null) ? 0 : invoice.getId().hashCode());
        result = prime * result + ((product == null) ? 0 : product.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof InvoiceProductPK))
            return false;
        InvoiceProductPK other = (InvoiceProductPK) obj;
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
