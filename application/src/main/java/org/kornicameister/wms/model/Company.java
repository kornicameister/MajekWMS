package org.kornicameister.wms.model;


import javax.persistence.*;

@Entity
@Table(name = "company",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idCompany", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name", updatable = true, insertable = true, nullable = false, length = 20))
})
public class Company extends NamedPersistenceObject {
    private static final long serialVersionUID = 7696614972997266832L;

    @Basic
    @Column(name = "longname", length = 45, updatable = true, insertable = true, nullable = false)
    private String longName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
    private Warehouse warehouse;

    public Company() {
        super();
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        return !(longName != null ? !longName.equals(company.longName) : company.longName != null)
                && !(warehouse != null ? !warehouse.equals(company.warehouse) : company.warehouse != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (longName != null ? longName.hashCode() : 0);
        result = 31 * result + (warehouse != null ? warehouse.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Company");
        sb.append("{longName='").append(longName).append('\'');
        sb.append(", warehouse=").append(warehouse);
        sb.append('}');
        return sb.toString();
    }
}
