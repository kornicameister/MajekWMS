package wms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "invoice", uniqueConstraints = {@UniqueConstraint(columnNames = {"refNumber"})})
@AttributeOverride(name = "id", column = @Column(name = "idInvoice", updatable = false, insertable = true, nullable = false))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "undefined")
public class Invoice extends PersistenceObject {
    @Transient
    private static final long serialVersionUID = - 3204092137188652431L;

    @Column(name = "refNumber", updatable = false, insertable = true, nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "client_id", referencedColumnName = "idClient")
    private Client client;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result
                + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
        result = prime * result
                + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
        result = prime * result
                + ((invoiceProducts == null) ? 0 : invoiceProducts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (! super.equals(obj))
            return false;
        if (! (obj instanceof Invoice))
            return false;
        Invoice other = (Invoice) obj;
        if (client == null) {
            if (other.client != null)
                return false;
        } else if (! client.equals(other.client))
            return false;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        } else if (! createdDate.equals(other.createdDate))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (! description.equals(other.description))
            return false;
        if (dueDate == null) {
            if (other.dueDate != null)
                return false;
        } else if (! dueDate.equals(other.dueDate))
            return false;
        if (invoiceNumber == null) {
            if (other.invoiceNumber != null)
                return false;
        } else if (! invoiceNumber.equals(other.invoiceNumber))
            return false;
        if (invoiceProducts == null) {
            if (other.invoiceProducts != null)
                return false;
        } else if (! invoiceProducts.equals(other.invoiceProducts))
            return false;
        return true;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Invoice");
        sb.append("{invoiceNumber='").append(invoiceNumber).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", description='").append(description).append('\'');
        sb.append(", invoiceProducts=").append(invoiceProducts);
        sb.append(", client=").append(client);
        sb.append('}');
        return sb.toString();
    }
}
