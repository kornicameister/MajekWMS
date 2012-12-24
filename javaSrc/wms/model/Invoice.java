package wms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "invoice", uniqueConstraints = {@UniqueConstraint(columnNames = {"refNumber"})})
@AttributeOverride(name = "id", column = @Column(name = "idInvoice", updatable = false, insertable = true, nullable = false))
public class Invoice extends PersistenceObject {
    @Transient
    private static final long serialVersionUID = -3204092137188652431L;

    @Column(name = "refNumber", length = 18, updatable = false, insertable = true, nullable = false)
    private String invoiceNumber = null;

    @Basic
    @Column(name = "createdDate", nullable = false)
    private Date createdDate = null;

    @Basic
    @Column(name = "dueDate", nullable = false)
    private Date dueDate = null;

    @Basic
    @Column(name = "description", nullable = true, updatable = true, insertable = false, length = 150)
    private String description = null;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "idClient")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoiceType_id", referencedColumnName = "idInvoiceType", updatable = true, nullable = false)
    private InvoiceType type;

    public Invoice() {
        super(); // hibernate
    }

    public synchronized final void setClient(Client client) {
        this.client = client;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        if (!super.equals(o)) return false;

        Invoice invoice = (Invoice) o;

        if (client != null ? !client.equals(invoice.client) : invoice.client != null) return false;
        if (!invoiceNumber.equals(invoice.invoiceNumber)) return false;

        return type.equals(invoice.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + invoiceNumber.hashCode();
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Invoice");
        sb.append("{invoiceNumber='").append(invoiceNumber).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", description='").append(description).append('\'');
        sb.append(", client=").append(client);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
