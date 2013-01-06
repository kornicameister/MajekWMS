package wms.controller.base.extractor;

import wms.controller.*;
import wms.model.*;

/**
 * This enum is used mainly when creating
 *
 * @author kornicameister
 */
public enum Entity {
    INVOICE(InvoiceController.class, Invoice.class),
    INVOICEPRODUCT(InvoiceProductController.class, InvoiceProduct.class),
    UNIT(UnitController.class, Unit.class),
    COMPANY(CompanyController.class, Company.class),
    UNITTYPE(UnitTypeController.class, UnitType.class),
    MEASURE(MeasureController.class, Measure.class),
    PRODUCT(ProductController.class, Product.class),
    CLIENT(ClientController.class, Client.class),
    BASIC(RequestController.class, BasicPersistentObject.class),
    ADDRESS(AddressController.class, Address.class);

    private Class<? extends BasicPersistentObject> entityClass;
    private final Class<? extends RequestController> entityControllerClass;

    private Entity(Class<? extends RequestController> ec, Class<? extends BasicPersistentObject> bc) {
        this.entityControllerClass = ec;
        this.entityClass = bc;
    }

    @Override
    public String toString() {
        return this.entityClass.getSimpleName();
    }

    public Class<? extends BasicPersistentObject> getEntityClass() {
        return entityClass;
    }

    public Class<? extends RequestController> getEntityControllerClass() {
        return entityControllerClass;
    }

    public void setEntityClass(Class<? extends BasicPersistentObject> entityClass) {
        this.entityClass = entityClass;
    }

}
