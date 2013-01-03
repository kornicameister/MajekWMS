package wms.controller;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import wms.controller.base.extractor.RData;
import wms.model.*;
import wms.utilities.Pair;
import wms.utilities.SortedList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

/**
 * @author kornicameister
 * @version 0.0.1
 *          <p/>
 *          This class is used to handle actions
 *          related to {@link InvoiceProduct} class.
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
        class AllocationAlgorithmExecutor implements Runnable {
            InvoiceType type = invoiceCache.getType();
            Queue<Unit> units = new SortedList<>(new Comparator<Unit>() {
                @Override
                public int compare(Unit unit, Unit unit2) {
                    return unit.getLeftSize().compareTo(unit2.getLeftSize()) * (-1);
                }
            });
            List<Pair<Product, Long>> allocatedProducts = new ArrayList<>();

            AllocationAlgorithmExecutor(Session session) {
                this.units.addAll(session.createQuery("from Unit").list());
            }

            @Override
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
            }

            /**
             * This method allocates resources by supply.
             * It means that will update warehouse stacks
             * by addition.
             */
            void allocateBySupply() {
                for (Pair<Product, Long> product : unallocatedProducts) {
                    this.supply(product.getSecond(), product.getFirst());
                    this.allocatedProducts.add(product);
                }
            }

            void supply(Long productPallets, final Product product) {
                Long unitSize = units.element().getLeftSize();
                if (productPallets > 0) {
                    boolean fullLoad = ((unitSize - productPallets) >= 0);
                    UnitProduct unitProduct = new UnitProduct();

                    unitProduct.setProduct(product);

                    if (fullLoad) {
                        unitProduct.setPallets(productPallets);
                        unitProduct.setUnit(units.element());

                        unitSize = units.element().getLeftSize() - productPallets;
                        units.element().setLeftSize(unitSize);

                        productPallets = 0l;
                    } else {
                        unitProduct.setPallets(units.peek().getLeftSize());
                        unitProduct.setUnit(units.remove());

                        productPallets -= unitProduct.getUnit().getLeftSize();
                    }

                    session.saveOrUpdate(unitProduct);
                    this.supply(productPallets, product);
                }
            }
        }

        new AllocationAlgorithmExecutor(this.session).run();
    }

    @Override
    BasicPersistentObject preCreate(BasicPersistentObject b, JSONObject payloadData) {
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
