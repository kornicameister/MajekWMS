package org.kornicameister.wms.model.hibernate.embeddable;

import org.kornicameister.wms.model.hibernate.Product;
import org.kornicameister.wms.model.hibernate.Unit;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * This class represents embeddable many-to-many
 * key that represents association between
 * unit and product
 *
 * @author kornicameister
 * @see org.kornicameister.wms.model.hibernate.UnitProduct
 * @since 0.0.1
 */
@Embeddable
public class UnitProductPK implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Unit unit;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Product product;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitProductPK)) return false;

        UnitProductPK that = (UnitProductPK) o;

        return product.equals(that.product) && unit.equals(that.unit);
    }

    @Override
    public int hashCode() {
        int result = unit.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UnitProductPK");
        sb.append("{unit=").append(unit);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
