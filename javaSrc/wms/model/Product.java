package wms.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "product")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "idProduct", updatable = false, insertable = true, nullable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name", insertable = true, updatable = true, nullable = false, length = 45, unique = false))})
public class Product extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 1246737308278979025L;

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    private String description = null;

    @Basic
    @Column(name = "pallets", nullable = false, insertable = true, updatable = true)
    private Integer pallets = null;

    @Basic
    @Column(name = "quantity", nullable = false, insertable = true, updatable = true)
    private Double quantity = null;

    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true)
    private Double price = null;

    @Basic
    @Column(name = "tax", nullable = false, insertable = true, updatable = true)
    private Float tax = null;

    @JoinColumn(name = "measure_id", referencedColumnName = "idMeasure")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Measure measure;

    public Product() {
        super();
    }

    public synchronized final void setMeasure(Measure measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;

        Product product = (Product) o;

        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (!measure.equals(product.measure)) return false;
        if (!price.equals(product.price)) return false;
        if (!tax.equals(product.tax)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + price.hashCode();
        result = 31 * result + tax.hashCode();
        result = 31 * result + measure.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Product");
        sb.append("{description='").append(description).append('\'');
        sb.append(", pallets=").append(pallets);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", tax=").append(tax);
        sb.append(", measure=").append(measure);
        sb.append('}');
        return sb.toString();
    }
}
