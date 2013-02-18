package org.kornicameister.wms.model.hibernate;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "unit")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idUnit", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, nullable = false, length = 45, unique = false))})
public class Unit extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 2437063899438647082L;

    @Column(name = "description", nullable = true, length = 666)
    private String description = null;

    @Basic
    @Column(name = "size", nullable = false)
    private Long size = 0l;

    @Formula("(select sum(up.pallets) / size from unitProduct up where up.unit_id = idUnit)")
    private Double usage = 0.0;

    @Formula("(select size - sum(up.pallets) from unitProduct up where up.unit_id = idUnit)")
    private Long leftSize = 0l;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "idWarehouse")
    private Warehouse warehouse = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unittype_id", referencedColumnName = "idUnitType")
    private UnitType type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "unitProduct",
            joinColumns = {@JoinColumn(name = "unit_id", referencedColumnName = "idUnit")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "idProduct")})
    private Set<Product> products = new HashSet<>();

    public Unit() {
        super();
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getLeftSize() {
        return (leftSize == null ? 0l : leftSize);
    }

    public Double getUsage() {
        return (this.usage == null ? 0l : this.usage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        if (!super.equals(o)) return false;

        Unit unit = (Unit) o;

        if (description != null ? !description.equals(unit.description) : unit.description != null) return false;
        if (size != null ? !size.equals(unit.size) : unit.size != null) return false;
        return !(type != null ? !type.equals(unit.type) : unit.type != null) && !(usage != null ? !usage.equals(unit.usage) : unit.usage != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Unit");
        sb.append("{type=").append(type);
        sb.append(", usage=").append(usage);
        sb.append(", size=").append(size);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
