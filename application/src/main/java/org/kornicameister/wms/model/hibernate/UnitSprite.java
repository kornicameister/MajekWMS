package org.kornicameister.wms.model.hibernate;

import javax.persistence.*;

/**
 * Created for EManager [ org.kornicameister.wms.model.hibernate ]
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(
        name = "unit_sprites",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "tileId"
                )
        }
)
@Cacheable
@AttributeOverride(
        name = "id",
        column = @Column(
                name = "idSprite",
                updatable = false,
                insertable = true,
                nullable = false)
)
public class UnitSprite extends PersistenceObject {

    private Integer tileId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "unit_id",
            referencedColumnName = "idUnit"
    )
    private Unit unit;

    public UnitSprite() {
        super();
    }

    public Integer getTileId() {
        return tileId;
    }

    public void setTileId(Integer tileId) {
        this.tileId = tileId;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitSprite)) return false;
        if (!super.equals(o)) return false;

        UnitSprite that = (UnitSprite) o;

        return !(tileId != null ? !tileId.equals(that.tileId) : that.tileId != null)
                && !(unit != null ? !unit.equals(that.unit) : that.unit != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (tileId != null ? tileId.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UnitSprite");
        sb.append("{unit=").append(unit);
        sb.append(", tileId='").append(tileId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
