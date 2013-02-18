package org.kornicameister.wms.model.logic.algorithms;

import java.util.List;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public interface Allocatable {
    List run();

    /**
     * This method allocates resources by supply.
     * It means that will update warehouse stacks
     * by addition.
     */
    void allocateBySupply();

    void allocateByReceipt();
}
