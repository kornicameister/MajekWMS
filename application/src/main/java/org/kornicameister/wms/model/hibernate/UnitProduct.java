package org.kornicameister.wms.model.hibernate;

import com.google.gson.annotations.SerializedName;
import org.kornicameister.wms.model.hibernate.embeddable.UnitProductPK;

import javax.persistence.*;

/**
 * Class represents association between
 * Units and Products in the following way.
 * It is possible that one product is located
 * in many different units at the same time
 * and one unit can be home for multiple
 * products at the same time.
 * Of course there is one field added <b>{@link UnitProduct#pallets}</b>,
 * which tells us how many space is currently occupied
 * by particular product in particular product.
 * <p/>
 * <b>
 * Sum of the pallets for single unit
 * must not exceed available size
 * of the unit.
 * </b>
 *
 * @author kornicameister
 * @since 0.0.1
 */
@Entity
@Table(name = "unitProduct")
@AssociationOverrides({
        @AssociationOverride(name = "pk.unit", joinColumns = @JoinColumn(name = "unit_id")),
        @AssociationOverride(name = "pk.product", joinColumns = @JoinColumn(name = "product_id"))})
public class UnitProduct extends BasicPersistentObject {

    @EmbeddedId
    @SerializedName("unitProduct")
    private UnitProductPK pk = new UnitProductPK();

    @Column(name = "pallets", updatable = true, nullable = true)
    private Long pallets = null;

    public Unit getUnit() {
        return this.pk.getUnit();
    }

    public void setProduct(Product product) {
        this.pk.setProduct(product);
    }

    public Product getProduct() {
        return this.pk.getProduct();
    }

    public void setUnit(Unit unit) {
        this.pk.setUnit(unit);
    }

    public Long getPallets() {
        return this.pallets;
    }

    public void setPallets(Long pallets) {
        this.pallets = pallets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitProduct)) return false;
        if (!super.equals(o)) return false;

        UnitProduct that = (UnitProduct) o;

        return !(pallets != null ? !pallets.equals(that.pallets) : that.pallets != null) &&
                !(pk != null ? !pk.equals(that.pk) : that.pk != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (pk != null ? pk.hashCode() : 0);
        result = 31 * result + (pallets != null ? pallets.hashCode() : 0);
        return result;
    }
}
