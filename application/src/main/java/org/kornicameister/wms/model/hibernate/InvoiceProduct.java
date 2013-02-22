package org.kornicameister.wms.model.hibernate;

import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.kornicameister.wms.model.hibernate.embeddable.InvoiceProductPK;

import javax.persistence.*;


@Entity
@Table(
        name = "invoiceProduct"
)
@org.hibernate.annotations.Cache(
        region = "invoiceproduct_cache",
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Cacheable(
        value = true
)
@BatchSize(
        size = 20
)
@AssociationOverrides({
        @AssociationOverride(
                name = "pk.invoice",
                joinColumns = @JoinColumn(name = "invoice_id")),
        @AssociationOverride(
                name = "pk.product",
                joinColumns = @JoinColumn(name = "product_id"))
})
public class InvoiceProduct extends BasicPersistentObject {
    @Transient
    private static final long serialVersionUID = 1269575448414133565L;

    @EmbeddedId
    @SerializedName("invoiceProduct")
    private InvoiceProductPK pk = new InvoiceProductPK();

    @Basic
    @Column(name = "pallets", insertable = true, nullable = false, updatable = true)
    private Double pallets = null;

    @Basic
    @Column(name = "price", insertable = true, nullable = false, updatable = true)
    private Float price = null;

    @Basic
    @Column(name = "tax", insertable = true, nullable = false, updatable = true)
    private Integer tax = null;

    @Basic
    @Column(name = "comment", insertable = true, nullable = false, updatable = true, length = 45)
    private String comment = null;

    public InvoiceProduct() {
        super(); // hibernate
        this.pk = new InvoiceProductPK();
    }

    public final InvoiceProductPK getPk() {
        return pk;
    }

    public Invoice getInvoice() {
        return this.getPk().getInvoice();
    }

    public Product getProduct() {
        return this.getPk().getProduct();
    }

    public final void setProduct(Product product) {
        this.getPk().setProduct(product);
    }

    public final void setInvoice(Invoice invoice) {
        this.getPk().setInvoice(invoice);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof InvoiceProduct
                && super.equals(o)
                && pk.equals(((InvoiceProduct) o).pk);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pk.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("InvoiceProduct");
        sb.append("{pk=").append(pk);
        sb.append(", quantity=").append(pallets);
        sb.append(", price=").append(price);
        sb.append(", tax=").append(tax);
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
