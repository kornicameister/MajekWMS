package wms.model;

import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import wms.controller.base.annotations.HideAssociation;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("ALL")
@Entity
@Table(name = "warehouse",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Warehouse extends BasicPersistentObject {
    @Transient
    private static final long serialVersionUID = 4557522901223374020L;

    @Id
    @Column(name = "idWarehouse", updatable = false, insertable = true, nullable = false)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "company"))
    @SerializedName(value = "id")
    private Long companyId;

    @Basic
    @Column(name = "name", length = 20, unique = true, updatable = true, nullable = false)
    private String name;

    @Basic
    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

    @Column(name = "description", nullable = true, length = 666)
    private String description;

    @Formula("(SELECT sum(u.size)/size from unit u where u.warehouse_id = idWarehouse)")
    private Float usage;

    @Basic
    @Column(name = "size", nullable = false)
    private Long size;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @HideAssociation
    private Company company;

    public Warehouse() {
        super();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long id) {
        this.companyId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUsage() {
        return usage;
    }

    public void setUsage(Float usage) {
        this.usage = usage;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warehouse)) return false;
        if (!super.equals(o)) return false;

        Warehouse warehouse = (Warehouse) o;

        if (companyId != null ? !companyId.equals(warehouse.companyId) : warehouse.companyId != null) return false;
        if (createdDate != null ? !createdDate.equals(warehouse.createdDate) : warehouse.createdDate != null)
            return false;
        if (description != null ? !description.equals(warehouse.description) : warehouse.description != null)
            return false;
        if (name != null ? !name.equals(warehouse.name) : warehouse.name != null) return false;
        if (size != null ? !size.equals(warehouse.size) : warehouse.size != null) return false;
        if (usage != null ? !usage.equals(warehouse.usage) : warehouse.usage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Warehouse");
        sb.append("{size=").append(size);
        sb.append(", companyId=").append(companyId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", description='").append(description).append('\'');
        sb.append(", usage=").append(usage);
        sb.append('}');
        return sb.toString();
    }
}
