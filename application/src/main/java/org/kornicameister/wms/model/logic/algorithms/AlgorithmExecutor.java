package org.kornicameister.wms.model.logic.algorithms;

import org.kornicameister.wms.model.hibernate.Product;
import org.kornicameister.wms.model.hibernate.Unit;
import org.kornicameister.wms.model.hibernate.UnitProduct;
import org.kornicameister.wms.model.logic.algorithms.exception.AvailableUnitProcessingException;
import org.kornicameister.wms.model.logic.algorithms.exception.InsufficientAlgorithmConfigurationException;
import org.kornicameister.wms.utilities.Pair;
import org.kornicameister.wms.utilities.SortedList;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class AlgorithmExecutor implements Allocatable {
    private AlgorithmProcessable bySupplyAlgorithm, byReceiptAlgorithm;
    protected Queue<AvailableUnit> availableUnits;
    private List<Pair<Product, Long>> unallocatedProducts;

    @Override
    public final Set<UnitProduct> run(AlgorithmTarget target)
            throws InsufficientAlgorithmConfigurationException {

        if (this.bySupplyAlgorithm == null
                || this.byReceiptAlgorithm == null
                || this.availableUnits == null
                || this.unallocatedProducts == null) {
            throw new InsufficientAlgorithmConfigurationException();
        }

        Set<UnitProduct> data = null;
        switch (target) {
            case RECEIPT:
                data = this.allocateByReceipt();
                break;
            case SUPPLY:
                data = this.allocateBySupply();
                break;
        }
        return data;
    }

    @Override
    public Allocatable setBySupplyAlgorithm(AlgorithmProcessable algorithm) {
        this.bySupplyAlgorithm = algorithm;
        return this;
    }

    @Override
    public Allocatable setByReceiptAlgorithm(AlgorithmProcessable algorithm) {
        this.byReceiptAlgorithm = algorithm;
        return this;
    }

    @Override
    public Allocatable setAvailableUnits(List<Unit> units)
            throws AvailableUnitProcessingException {
        this.availableUnits = new SortedList<>(new Comparator<AvailableUnit>() {

            @Override
            public int compare(AvailableUnit availableUnit, AvailableUnit availableUnit2) {
                return availableUnit.compareTo(availableUnit2) * (-1);
            }
        });
        for (Unit unit : units) {
            if (!this.availableUnits.offer(new AvailableUnit(unit))) {
                throw new AvailableUnitProcessingException();
            }
        }
        return this;
    }

    @Override
    public Allocatable setUnallocatedProducts(List<Pair<Product, Long>> unallocatedProducts) {
        this.unallocatedProducts = unallocatedProducts;
        return this;
    }

    /**
     * This method allocates resources by supply.
     * It means that will update warehouse stacks
     * by addition.
     */
    private Set<UnitProduct> allocateBySupply() {
        if (this.bySupplyAlgorithm.execute(this.availableUnits, this.unallocatedProducts)) {
            return this.bySupplyAlgorithm.getResult();
        }
        return null;
    }

    /**
     * This method however will allocate all resources by
     * the means of recipient and therefore all stocks
     * are going to be increased
     */
    private Set<UnitProduct> allocateByReceipt() {
        if (this.byReceiptAlgorithm.execute(this.availableUnits, this.unallocatedProducts)) {
            return this.byReceiptAlgorithm.getResult();
        }
        return null;
    }


}
