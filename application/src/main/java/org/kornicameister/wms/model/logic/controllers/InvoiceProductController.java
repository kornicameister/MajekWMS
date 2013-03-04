package org.kornicameister.wms.model.logic.controllers;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.kornicameister.wms.cm.annotations.ServerController;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.*;
import org.kornicameister.wms.model.logic.algorithms.*;
import org.kornicameister.wms.model.logic.algorithms.exception.AvailableUnitProcessingException;
import org.kornicameister.wms.model.logic.algorithms.exception.InsufficientAlgorithmConfigurationException;
import org.kornicameister.wms.server.extractor.RData;
import org.kornicameister.wms.utilities.Pair;
import org.kornicameister.wms.utilities.SortedList;

import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 *          <p/>
 *          This class is used to handle actions
 *          related to {@link org.kornicameister.wms.model.hibernate.InvoiceProduct} class.
 * @created 27.12.12
 */
@ServerController(mapping = "wms/agent/invoiceproduct", model = InvoiceProduct.class)
public class InvoiceProductController extends RequestController {
    private final static Logger logger = Logger.getLogger(InvoiceProductController.class.getName());
    private Invoice invoiceCache;
    private List<Pair<Product, Long>> unallocatedProducts;

    public InvoiceProductController() {

    }

    public InvoiceProductController(RData data) {
        super(data);
        this.unallocatedProducts = new SortedList<>(new Comparator<Pair<Product, Long>>() {
            @Override
            public int compare(Pair<Product, Long> a, Pair<Product, Long> b) {
                return a.getSecond().compareTo(b.getSecond());
            }
        });
    }

    @Override
    public void create() {
        try {

            this.parsePayload();

            List<Unit> units = new ArrayList<>();
            List data = this.session.createQuery("from Unit").list();
            for (Object object : data) {
                if (object instanceof Unit) {
                    units.add((Unit) object);
                }
            }

            Allocatable allocator = new AlgorithmExecutor()
                    .setAvailableUnits(units)
                    .setUnallocatedProducts(this.unallocatedProducts)
                    .setByReceiptAlgorithm(new AlgorithmProcessable() {
                        @Override
                        public boolean execute(Queue<AvailableUnit> availableUnits, List<Pair<Product, Long>> unallocatedProducts) {
                            return false;
                        }

                        @Override
                        public Set<UnitProduct> getResult() {
                            return null;
                        }
                    })
                    .setBySupplyAlgorithm(new AlgorithmProcessable() {
                        private List<Product> allocatedProducts = new ArrayList<>();
                        private Queue<AvailableUnit> availableUnits;
                        private Set<UnitProduct> results = new HashSet<>();

                        @Override
                        public boolean execute(Queue<AvailableUnit> availableUnits,
                                               List<Pair<Product, Long>> unallocatedProducts) {

                            this.availableUnits = availableUnits;

                            for (Pair<Product, Long> productLongPair : unallocatedProducts) {
                                this.supply(productLongPair.getSecond(), productLongPair.getFirst());
                                this.allocatedProducts.add(productLongPair.getFirst());
                            }

                            return this.allocatedProducts.size() > 0;
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
                         *     {@link org.kornicameister.wms.model.hibernate.Product#pallets} is the greatest.
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
	Long unitSize = this.availableUnits.element().getSize();

	if (productPallets > 0 && unitSize > 0) {
		boolean fullLoad = ((unitSize - productPallets) >= 0);
		UnitProduct unitProduct = new UnitProduct();

		unitProduct.setProduct(product);

		if (fullLoad) {
			unitProduct.setPallets(productPallets);
			unitProduct.setUnit(this.availableUnits.element().getUnit());

			unitSize = this.availableUnits.element().getSize() - productPallets;
			this.availableUnits.element().setSize(unitSize);

			productPallets = 0l;
		} else {
			unitProduct.setPallets(this.availableUnits.peek().getSize());
			unitProduct.setUnit(this.availableUnits.remove().getUnit());

			productPallets -= unitProduct.getUnit().getLeftSize();
		}

		logger.info(String.format("Updated stocks at %s", fullLoad 
			? "full load" : "partial load")
		);
		this.results.add(unitProduct);

		this.supply(productPallets, product);
	} else if (unitSize == 0l) {
		logger.info(String.format("%d has no left stocks available...", 
			this.availableUnits.remove().getUnit().getId())
			);
	} else {
		logger.info(String.format("Finished allocating [ %d ]", product.getId()));
	}
}

                        @Override
                        public Set<UnitProduct> getResult() {
                            return this.results;
                        }
                    });

            Set<UnitProduct> allocatedUnitProducts = allocator
                    .run((this.invoiceCache.getType().getName().equals("supply") ? AlgorithmTarget.SUPPLY : AlgorithmTarget.RECEIPT));

            for (UnitProduct unitProduct : allocatedUnitProducts) {
                this.session.saveOrUpdate(unitProduct);
            }
        } catch (AvailableUnitProcessingException processingException) {
            logger.warn("Failed to initialize available unit list", processingException);
        } catch (InsufficientAlgorithmConfigurationException configurationException) {
            logger.error("Insufficient algorithm configuration detected", configurationException);
        }

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
