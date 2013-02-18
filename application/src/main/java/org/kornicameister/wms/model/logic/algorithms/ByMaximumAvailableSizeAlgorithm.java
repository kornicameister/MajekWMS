package org.kornicameister.wms.model.logic.algorithms;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.kornicameister.wms.model.hibernate.InvoiceType;
import org.kornicameister.wms.model.hibernate.Product;
import org.kornicameister.wms.model.hibernate.UnitProduct;
import org.kornicameister.wms.utilities.Pair;
import org.kornicameister.wms.utilities.SortedList;
import org.kornicameister.wms.utilities.hibernate.HibernateBridge;

import java.util.*;

/**
 * This algorithm executes either allocation or dislocation
 * products within the warehouse structure. It follows the
 * approach of, take or push from/to where there is minimum size used
 * or maximum size left.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class ByMaximumAvailableSizeAlgorithm implements Allocatable {
    private static final Logger LOGGER = Logger.getLogger(ByMaximumAvailableSizeAlgorithm.class);
    private final Queue<Pair<UnitProduct, Long>> pairQueueUnits = new SortedList<>(new Comparator<Pair<UnitProduct, Long>>() {
        @Override
        public int compare(Pair<UnitProduct, Long> unitLongPair, Pair<UnitProduct, Long> unitLongPair2) {
            return unitLongPair.getSecond().compareTo(unitLongPair2.getSecond()) * (-1);
        }
    });
    private final List<Pair<Product, Long>> allocatedProducts = new ArrayList<>();
    private final Session session;
    private final List<Pair<Product, Long>> unallocatedProducts;
    private final InvoiceType type;

    public ByMaximumAvailableSizeAlgorithm(List<Pair<Product, Long>> unallocatedProducts,
                                           InvoiceType type) {
        this.session = HibernateBridge.getSessionFactory().openSession();
        this.unallocatedProducts = unallocatedProducts;
        this.type = type;
    }


    @Override
    public List run() {
        this.retrieveAvailableUnits();
        this.session.beginTransaction();
        switch (this.type.getName()) {
            case "supply":
                this.allocateBySupply();
                break;
            case "receipt":
                this.allocateByReceipt();
                break;
        }
        this.session.flush();
        this.session.getTransaction().commit();

        this.unallocatedProducts.removeAll(this.allocatedProducts);
        return this.unallocatedProducts;
    }

    @Override
    public void allocateByReceipt() {
        LOGGER.info(String.format("%d products to be dislocated", unallocatedProducts.size()));
    }


    @Override
    public void allocateBySupply() {
        LOGGER.info(String.format("%d products to be allocated", unallocatedProducts.size()));
        for (Pair<Product, Long> product : unallocatedProducts) {
            this.supply(product.getSecond(), product.getFirst());
            this.allocatedProducts.add(product);
        }
    }

    private void retrieveAvailableUnits() {
        this.session.beginTransaction();
        {
            Collection data = session.createQuery("from UnitProduct ").list();
            for (Iterator iterator = data.iterator(); iterator.hasNext(); ) {
                Object object = iterator.next();
                if (object instanceof UnitProduct) {
                    this.pairQueueUnits.add(new Pair(object, ((UnitProduct) object).getUnit().getLeftSize()));
                }
            }
        }
        this.session.getTransaction().commit();
        this.session.clear();
    }

    /**
     * This method is a convenient method that handles
     * updating unit's stocks status, by associating
     * products with particular unit.
     * <p/>
     * At the moment supply algorithm supports the most
     * basic allocation:
     * <pre>
     *     Products are allocated starting from the one which
     *     {@link Product#pallets} is the greatest.
     *     The same rule is applied to pairQueueUnits.
     *     <p>
     *          Allocations start from the unit
     *          which usage is the smallest.
     *     </p>
     * </pre>
     * <p/>
     * <b style="color: red">
     * This method uses recursion, stops
     * when productPallets equals to 0
     * </b>
     *
     * @param productPallets how many pallets should be allocated
     * @param product        product which we allocate
     */
    private void supply(Long productPallets, final Product product) {
        Long unitSize = pairQueueUnits.element().getSecond();
        if (productPallets > 0 && unitSize > 0) {
            boolean fullLoad = ((unitSize - productPallets) >= 0);

            UnitProduct unitProduct = null;
            // locate matching product
            for (Pair<UnitProduct, Long> pairQueueUnit : pairQueueUnits) {
                if (pairQueueUnit.getFirst().getProduct().getId().equals(product.getId())) {
                    unitProduct = pairQueueUnit.getFirst();
                    break;
                }
            }
            if (unitProduct == null) {
                unitProduct = new UnitProduct();
                unitProduct.setProduct(product);
            }

            if (fullLoad) {
                unitProduct.setPallets(unitProduct.getPallets() + productPallets);
                unitProduct.setUnit(pairQueueUnits.element().getFirst().getUnit());

                unitSize = pairQueueUnits.element().getSecond() - productPallets;
                pairQueueUnits.element().setSecond(unitSize);

                productPallets = 0l;
            } else {
                unitProduct.setPallets(unitProduct.getPallets() + pairQueueUnits.peek().getSecond());
                unitProduct.setUnit(pairQueueUnits.remove().getFirst().getUnit());

                productPallets -= unitProduct.getUnit().getLeftSize();
            }


            LOGGER.info(String.format("Updated stocks at %s", fullLoad ? "full load" : "partial load"));
            this.session.saveOrUpdate(unitProduct);
            this.supply(productPallets, product);
        } else if (unitSize == 0l) {
            LOGGER.info(String.format("%d has no left stocks available...", pairQueueUnits.remove().getFirst().getUnit().getId()));
        } else {
            LOGGER.info(String.format("Finished allocating [ %d ]", product.getId()));
        }
    }
}
