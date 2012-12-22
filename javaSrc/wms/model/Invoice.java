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
    private String invoiceNumber;

    @Basic
    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

    @Basic
    @Column(name = "dueDate", nullable = false)
    private Date dueDate;

    @Basic
    @Column(name = "description", nullable = true, updatable = true, insertable = false, length = 150)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.invoice")
    private Set<InvoiceProduct> invoiceProducts = new HashSet<>(0);

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "idClient")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoiceType_id", referencedColumnName = "idInvoiceType", updatable = true, nullable = false)
    private InvoiceType type;

    public Invoice() {
        super(); // hibernate
    }

    public synchronized final String getInvoiceNumber() {
        return invoiceNumber;
    }

    public synchronized final void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public synchronized final Date getCreatedDate() {
        return createdDate;
    }

    public synchronized final void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public synchronized final Date getDueDate() {
        return dueDate;
    }

    public synchronized final void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public synchronized final String getDescription() {
        return description;
    }

    public synchronized final void setDescription(String description) {
        this.description = description;
    }

    public synchronized final Set<InvoiceProduct> getInvoiceProducts() {
        return invoiceProducts;
    }

    public synchronized final void setInvoiceProducts(
            Set<InvoiceProduct> invoiceProducts) {
        this.invoiceProducts = invoiceProducts;
    }

    public synchronized final Client getClient() {
        return client;
    }

    public synchronized final void setClient(Client client) {
        this.client = client;
    }

    public InvoiceType getType() {
        return type;
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

        if (!client.equals(invoice.client)) return false;
        if (!createdDate.equals(invoice.createdDate)) return false;
        if (!description.equals(invoice.description)) return false;
        if (!dueDate.equals(invoice.dueDate)) return false;
        if (!invoiceNumber.equals(invoice.invoiceNumber)) return false;
        return invoiceProducts.equals(invoice.invoiceProducts) && type.equals(invoice.type);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + invoiceNumber.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + dueDate.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + invoiceProducts.hashCode();
        result = 31 * result + client.hashCode();
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
        sb.append(", invoiceProducts=").append(invoiceProducts);
        sb.append(", client=").append(client);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
