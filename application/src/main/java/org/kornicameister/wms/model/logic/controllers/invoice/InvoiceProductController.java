package org.kornicameister.wms.model.logic.controllers.invoice;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.kornicameister.wms.model.logic.RequestController;
import org.kornicameister.wms.server.extractor.RData;
import org.kornicameister.wms.model.hibernate.*;
import org.kornicameister.wms.utilities.Pair;
import org.kornicameister.wms.utilities.SortedList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

/**
 * @author kornicameister
 * @version 0.0.1
 *          <p/>
 *          This class is used to handle actions
 *          related to {@link org.kornicameister.wms.model.hibernate.InvoiceProduct} class.
 * @created 27.12.12
 */
public class InvoiceProductController extends RequestController {
    private Invoice invoiceCache;
    private final List<Pair<Product, Long>> unallocatedProducts = new SortedList<>(new Comparator<Pair<Product, Long>>() {

        @Override
        public int compare(Pair<Product, Long> a, Pair<Product, Long> b) {
            return a.getSecond().compareTo(b.getSecond());
        }
    }
    );
    private final static Logger logger = Logger
            .getLogger(InvoiceProductController.class.getName());

    public InvoiceProductController(RData data) {
        super(data);
    }

    @Override
    public void create() {
        super.create();

        /**
         * Allocation algorithm
         * 1. check invoices type
         * 2. get units (sort them by size in descending order)
         * 3. start putting products per unit
         */
        class AllocationAlgorithmExecutor {
            InvoiceType type = invoiceCache.getType();
            Queue<Pair<Unit, Long>> units = new SortedList<>(new Comparator<Pair<Unit, Long>>() {
                @Override
                public int compare(Pair<Unit, Long> unitLongPair, Pair<Unit, Long> unitLongPair2) {
                    return unitLongPair.getSecond().compareTo(unitLongPair2.getSecond()) * (-1);
                }
            });
            List<Pair<Product, Long>> allocatedProducts = new ArrayList<>();

            AllocationAlgorithmExecutor(Session session) {
                List<? extends Unit> data = session.createQuery("from Unit").list();
                for (Unit u : data) {
                    this.units.add(new Pair(u, u.getLeftSize()));
                }
            }

            public void run() {
                session.beginTransaction();
                switch (this.type.getName()) {
                    case "supply":
                        this.allocateBySupply();
                        break;
                    case "receipt":
                        this.allocateByReceipt();
                        break;
                }
                unallocatedProducts.removeAll(this.allocatedProducts);
                session.getTransaction().commit();
            }

            void allocateByReceipt() {
                logger.info(String.format("%d products to be dislocated", unallocatedProducts.size()));
            }

            /**
             * This method allocates resources by supply.
             * It means that will update warehouse stacks
             * by addition.
             */
            void allocateBySupply() {
                logger.info(String.format("%d products to be allocated", unallocatedProducts.size()));
                for (Pair<Product, Long> product : unallocatedProducts) {
                    this.supply(product.getSecond(), product.getFirst());
                    this.allocatedProducts.add(product);
                }
            }

            /**
             * This method is a convenient method that handles
             * updating unit's stocks status, by associating
             * products with particular unit.
             *
             * At the moment supply algorithm supports the most
             * basic allocation:
             * <pre>
             *     Products are allocated starting from the one which
             *     {@link Product#pallets} is the greatest.
             *     The same rule is applied to units.
             *     <p>
             *          Allocations start from the unit
             *          which usage is the smallest.
             *     </p>
             * </pre>
             *
             * <b style="color: red">
             *     This method uses recursion, stops
             *     when productPallets equals to 0
             * </b>
             * @param productPallets how many pallets should be allocated
             * @param product product which we allocate
             */
            void supply(Long productPallets, final Product product) {
                Long unitSize = units.element().getSecond();
                if (productPallets > 0 && unitSize > 0) {
                    boolean fullLoad = ((unitSize - productPallets) >= 0);
                    UnitProduct unitProduct = new UnitProduct();

                    unitProduct.setProduct(product);

                    if (fullLoad) {
                        unitProduct.setPallets(productPallets);
                        unitProduct.setUnit(units.element().getFirst());

                        unitSize = units.element().getSecond() - productPallets;
                        units.element().setSecond(unitSize);

                        productPallets = 0l;
                    } else {
                        unitProduct.setPallets(units.peek().getSecond());
                        unitProduct.setUnit(units.remove().getFirst());

                        productPallets -= unitProduct.getUnit().getLeftSize();
                    }

                    session.saveOrUpdate(unitProduct);
                    this.supply(productPallets, product);

                    logger.info(String.format("Updated stocks at %s", fullLoad ? "full load" : "partial load"));
                } else if (unitSize == 0l) {
                    logger.info(String.format("%d has no left stocks available...", units.remove().getFirst().getId()));
                } else {
                    logger.info(String.format("Finished allocating [ %d ]", product.getId()));
                }
            }
        }

        new AllocationAlgorithmExecutor(this.session).run();
    }

    @Override
    protected BasicPersistentObject preCreate(BasicPersistentObject b, JSONObject payloadData) {
        InvoiceProduct ip = (InvoiceProduct) b;

        // setting up pk
        Long invoice_id = (Long) ((JSONObject) payloadData.get("invoiceProduct")).get("invoice_id");
        Long product_id = (Long) ((JSONObject) payloadData.get("invoiceProduct")).get("product_id");

        // saving invoice to cache
        if (this.invoiceCache == null) {
            this.invoiceCache = (Invoice) this.session.byId(Invoice.class).load(invoice_id);
        } else {
            Long cachedId = this.invoiceCache.getId();
            if (!cachedId.equals(invoice_id)) {
                this.invoiceCache = (Invoice) this.session.byId(Invoice.class).load(invoice_id);
            }
        }

        // adding products to the queue
        Product product = (Product) this.session.byId(Product.class).load(product_id);
        this.unallocatedProducts.add(new Pair(product, payloadData.get("pallets")));

        ip.setInvoice(this.invoiceCache);
        ip.setProduct(product);

        return ip;
    }
}
