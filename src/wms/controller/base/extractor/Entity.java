package wms.controller.base.extractor;

import wms.controller.AddressController;
import wms.controller.CityController;
import wms.controller.ClientController;
import wms.controller.ClientDetailsController;
import wms.controller.MeasureController;
import wms.controller.ProductController;
import wms.controller.UnitController;
import wms.controller.UnitTypeController;
import wms.controller.WarehouseController;
import wms.controller.base.RequestController;
import wms.model.Warehouse;
import wms.model.basic.BasicPersistanceObject;
import wms.model.client.Address;
import wms.model.client.City;
import wms.model.client.Client;
import wms.model.client.ClientDetails;
import wms.model.product.Measure;
import wms.model.product.Product;
import wms.model.unit.Unit;
import wms.model.unit.UnitType;

/**
 * This enum is used mainly when creating 
 * @author kornicameister
 *
 */
public enum Entity {
	UNIT(UnitController.class,Unit.class), 
	WAREHOUSE(WarehouseController.class,Warehouse.class), 
	UNITTYPE(UnitTypeController.class,UnitType.class), 
	MEASURE(MeasureController.class,Measure.class),
	PRODUCT(ProductController.class,Product.class), 
	INVOICE(null,null), 
	CLIENT(ClientController.class,Client.class),
	INVOICETYPE(null,null),
	CITY(CityController.class,City.class),
	ADDRESS(AddressController.class, Address.class),
	CLIENTDETAILS(ClientDetailsController.class,ClientDetails.class);

	private final Class<? extends BasicPersistanceObject> entityClass;
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
	};
}
