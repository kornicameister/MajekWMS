package wms.model;

import javax.persistence.*;

@Entity
@Table(name = "client",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idClient", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, nullable = false, length = 45, unique = true))})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "client")
public class Client extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 1283426340575080285L;

    @Basic
    @Column(name = "company", insertable = true, nullable = false, unique = true, length = 45, updatable = true)
    private String company = null;

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    private String description = null;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private ClientDetails details = null;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "idAddress")
    private Address address;

    public Client() {
        super();
    }

    public synchronized final ClientDetails getDetails() {
        return details;
    }

    public synchronized final Address getAddress() {
        return address;
    }

    public synchronized final void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((details == null) ? 0 : details.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (! super.equals(obj))
            return false;
        if (! (obj instanceof Client))
            return false;
        Client other = (Client) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (! address.equals(other.address))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (! company.equals(other.company))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (! description.equals(other.description))
            return false;
        if (details == null) {
            if (other.details != null)
                return false;
        } else if (! details.equals(other.details))
            return false;
        return true;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Client");
        sb.append("{company='").append(company).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", details=").append(details);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }
}
