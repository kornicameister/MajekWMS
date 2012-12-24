package wms.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "invoiceProduct")
@AssociationOverrides({
        @AssociationOverride(name = "pk.invoice", joinColumns = @JoinColumn(name = "invoice_id")),
        @AssociationOverride(name = "pk.product", joinColumns = @JoinColumn(name = "product_id"))})
public class InvoiceProduct extends BasicPersistentObject {
    @Transient
    private static final long serialVersionUID = 1269575448414133565L;

    @EmbeddedId
    private InvoiceProductId pk = new InvoiceProductId();

    @Basic
    @Column(name = "quantity", insertable = true, nullable = false, updatable = true)
    private Double quantity = null;

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
    }

    public final InvoiceProductId getPk() {
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
        if (this == o) return true;
        if (!(o instanceof InvoiceProduct)) return false;
        if (!super.equals(o)) return false;

        return pk.equals(((InvoiceProduct) o).pk);
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
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", tax=").append(tax);
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
