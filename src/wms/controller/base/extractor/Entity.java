package wms.controller.base.extractor;

import wms.controller.AddressController;
import wms.controller.BasicController;
import wms.controller.ClientController;
import wms.controller.CompanyController;
import wms.controller.MeasureController;
import wms.controller.ProductController;
import wms.controller.UnitController;
import wms.controller.UnitTypeController;
import wms.controller.base.RequestController;
import wms.model.Address;
import wms.model.BasicPersistanceObject;
import wms.model.City;
import wms.model.Client;
import wms.model.Company;
import wms.model.Measure;
import wms.model.Product;
import wms.model.Unit;
import wms.model.UnitType;

/**
 * This enum is used mainly when creating 
 * @author kornicameister
 *
 */
public enum Entity {
	UNIT(UnitController.class,Unit.class), 
	COMPANY(CompanyController.class, Company.class), 
	UNITTYPE(UnitTypeController.class,UnitType.class), 
	MEASURE(MeasureController.class,Measure.class),
	PRODUCT(ProductController.class,Product.class), 
	INVOICE(null,null), 
	CLIENT(ClientController.class,Client.class),
	INVOICETYPE(null,null),
	BASIC(BasicController.class, BasicPersistanceObject.class),
	CITY(BasicController.class, City.class),
	ADDRESS(AddressController.class, Address.class);

	private Class<? extends BasicPersistanceObject> entityClass;
	private final Class<? extends RequestController> entityControllerClass;

	private Entity(Class<? extends RequestController> ec,Class<? extends BasicPersistanceObject> bc) {
		this.entityControllerClass = ec;
		this.entityClass = bc;
	}

	@Override
	public String toString() {
		return this.entityClass.getSimpleName();
	}

	public Class<? extends BasicPersistanceObject> getEntityClass() {
		return entityClass;
	}

	public Class<? extends RequestController> getEntityControllerClass() {
		return entityControllerClass;
	}
	
	public void setEntityClass(Class<? extends BasicPersistanceObject> entityClass) {
		this.entityClass = entityClass;
	}
	
}
