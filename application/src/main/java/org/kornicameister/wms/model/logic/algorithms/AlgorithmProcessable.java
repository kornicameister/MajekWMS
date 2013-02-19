package org.kornicameister.wms.model.logic.algorithms;

import org.kornicameister.wms.model.hibernate.Product;
import org.kornicameister.wms.model.hibernate.Unit;
import org.kornicameister.wms.model.hibernate.UnitProduct;
import org.kornicameister.wms.utilities.Pair;

import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public interface AlgorithmProcessable {
    boolean execute(Queue<AvailableUnit> availableUnits,
                    List<Pair<Product, Long>> unallocatedProducts);

    Set<UnitProduct> getResult();
}
