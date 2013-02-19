package org.kornicameister.wms.model.logic.algorithms;

import org.kornicameister.wms.model.hibernate.Unit;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class AvailableUnit implements Comparable<AvailableUnit> {
    Unit unit;
    Long size;

    public AvailableUnit(Unit unit) {
        this.unit = unit;
        this.size = unit.getLeftSize();
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public int compareTo(AvailableUnit availableUnit) {
        return this.size.compareTo(availableUnit.size);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AvailableUnit");
        sb.append("{unit=").append(unit);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.append(" [ ").append(super.toString()).append(" ]").toString();
    }
}
