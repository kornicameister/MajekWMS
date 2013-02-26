package org.kornicameister.wms.model.hibernate;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(
        name = "product"
)
@AttributeOverrides(value = {
        @AttributeOverride(
                name = "id",
                column = @Column(
                        name = "idProduct",
                        updatable = false,
                        insertable = true,
                        nullable = false)
        ),
        @AttributeOverride(
                name = "name",
                column = @Column(
                        name = "name",
                        insertable = true,
                        updatable = true,
                        nullable = false,
                        length = 45,
                        unique = false)
        )})
public class Product extends NamedPersistenceObject {
    @Transient
    private static final long serialVersionUID = 1246737308278979025L;

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    private String description = null;

    @Formula("(select sum(up.pallets) from unitProduct up where up.product_id = idProduct)")
    private Long pallets = null;

    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true)
    private Double price = null;

    @Basic
    @Column(name = "tax", nullable = false, insertable = true, updatable = true)
    private Float tax = null;

    @Formula("(select sum(up.pallets) from unitProduct up where up.product_id = idProduct)")
    private Double totalCount;

    @JoinColumn(name = "measure_id", referencedColumnName = "idMeasure")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Measure measure;

    public Product() {
        super();
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Long getPallets() {
        return pallets;
    }

    public void setPallets(long pallets) {
        this.pallets = pallets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;

        Product product = (Product) o;

        return !(description != null ? !description.equals(product.description) :
                product.description != null) &&
                measure.equals(product.measure) &&
                price.equals(product.price) &&
                tax.equals(product.tax);
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
        sb.append(", price=").append(price);
        sb.append(", tax=").append(tax);
        sb.append(", measure=").append(measure);
        sb.append('}');
        return sb.toString();
    }
}
