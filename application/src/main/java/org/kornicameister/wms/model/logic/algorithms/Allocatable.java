package org.kornicameister.wms.model.logic.algorithms;

import org.kornicameister.wms.model.hibernate.Product;
import org.kornicameister.wms.model.hibernate.Unit;
import org.kornicameister.wms.model.hibernate.UnitProduct;
import org.kornicameister.wms.model.logic.algorithms.exception.AvailableUnitProcessingException;
import org.kornicameister.wms.model.logic.algorithms.exception.InsufficientAlgorithmConfigurationException;
import org.kornicameister.wms.utilities.Pair;

import java.util.List;
import java.util.Set;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public interface Allocatable {
    /**
     * This method will do whole algorithm and return
     * list of all entities that should be persisted.
     *
     * @return to be persisted objects
     */
    Set<UnitProduct> run(AlgorithmTarget target)
            throws InsufficientAlgorithmConfigurationException;

    Allocatable setBySupplyAlgorithm(AlgorithmProcessable algorithm);

    Allocatable setByReceiptAlgorithm(AlgorithmProcessable algorithm);

    Allocatable setAvailableUnits(List<Unit> units) throws AvailableUnitProcessingException;

    Allocatable setUnallocatedProducts(List<Pair<Product, Long>> unallocatedProducts);

}
