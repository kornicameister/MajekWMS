package wms.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "invoiceProduct")
@AssociationOverrides({
        @AssociationOverride(name = "pk.invoice", joinColumns = @JoinColumn(name = "invoice_id")),
        @AssociationOverride(name = "pk.product", joinColumns = @JoinColumn(name = "product_id"))})
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
    public Invoice getInvoice() {
        return this.getPk().getInvoice();
    }

    @Transient
    public Product getProduct() {
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

    public final void setPk(InvoiceProductId pk) {
        this.pk = pk;
    }

    public final void setProduct(Product product) {
        this.getPk().setProduct(product);
    }

    public final void setInvoice(Invoice invoice) {
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
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
        } else if (! comment.equals(other.comment))
            return false;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (! pk.equals(other.pk))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (! price.equals(other.price))
            return false;
        if (quantity == null) {
            if (other.quantity != null)
                return false;
        } else if (! quantity.equals(other.quantity))
            return false;
        if (tax == null) {
            if (other.tax != null)
                return false;
        } else if (! tax.equals(other.tax))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InvoiceProduct [");
        if (pk != null) {
            builder.append("pk=");
            builder.append(pk);
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
