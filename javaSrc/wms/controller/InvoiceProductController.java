package wms.controller;

import org.json.simple.JSONObject;
import wms.controller.base.extractor.RData;
import wms.model.BasicPersistentObject;
import wms.model.Invoice;
import wms.model.InvoiceProduct;
import wms.model.Product;

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

    public InvoiceProductController(RData data) {
        super(data);
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

        ip.setInvoice(this.invoiceCache);
        ip.setProduct((Product) this.session.byId(Product.class).load(product_id));

        return ip;
    }
}
