package wms.model;

import javax.persistence.*;

@Entity
@Table(name = "client",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idClient", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, nullable = false, length = 45, unique = true))})
public class Client extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 1283426340575080285L;

    @Basic
    @Column(name = "company", insertable = true, nullable = false, length = 45, updatable = false)
    private String company = null;

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    private String description = null;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private ClientDetails details = null;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "idAddress")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientType_id", referencedColumnName = "idClientType")
    private ClientType type;

    public Client() {
        super();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientDetails getDetails() {
        return details;
    }

    public void setDetails(ClientDetails details) {
        this.details = details;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (!address.equals(client.address)) return false;
        if (!company.equals(client.company)) return false;
        if (!description.equals(client.description)) return false;
        return details.equals(client.details) && type.equals(client.type);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + company.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + details.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Client");
        sb.append("{company='").append(company).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", details=").append(details);
        sb.append(", address=").append(address);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
