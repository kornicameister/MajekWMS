package wms.model;

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
    private String description;

    @Basic
    @Column(name = "size", nullable = false)
    private Long size;

    @Formula("(select sum(p.pallets) / (select u.size from unit u where u.idUnit=idUnit) from product p where p.idProduct in (select up.product_id from unitProduct up where up.unit_id=idUnit))")
    private Float usage = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "idWarehouse")
    private Warehouse warehouse;

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

    public synchronized final String getDescription() {
        return description;
    }

    public synchronized final void setDescription(String description) {
        this.description = description;
    }

    public synchronized final Long getSize() {
        return size;
    }

    public synchronized final void setSize(Long size) {
        this.size = size;
    }

    public synchronized final Float getUsage() {
        return usage;
    }

    public synchronized final Warehouse getWarehouse() {
        return warehouse;
    }

    public synchronized final void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public synchronized final UnitType getType() {
        return type;
    }

    public synchronized final void setType(UnitType type) {
        this.type = type;
    }

    public synchronized final Set<Product> getProducts() {
        return products;
    }

    public synchronized final void setProducts(Set<Product> products) {
        this.products = products;
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
