package wms.controller.base.extractor;

import wms.controller.UnitController;
import wms.controller.UnitTypeController;
import wms.controller.WarehouseController;
import wms.controller.base.RequestController;
import wms.model.BaseEntity;
import wms.model.Unit;
import wms.model.UnitType;
import wms.model.Warehouse;

/**
 * This enum is used mainly when creating 
 * @author kornicameister
 *
 */
public enum Entity {
	UNIT(UnitController.class,Unit.class), 
	WAREHOUSE(WarehouseController.class,Warehouse.class), 
	UNITTYPE(UnitTypeController.class,UnitType.class), 
	PRODUCT(null,null), 
	INVOICE(null,null), 
	CLIENT(null,null), 
	INVOICETYPE(null,null);

	private final Class<? extends BaseEntity> entityClass;
	private final Class<? extends RequestController> entityControllerClass;

	private Entity(Class<? extends RequestController> ec,Class<? extends BaseEntity> bc) {
		this.entityControllerClass = ec;
		this.entityClass = bc;
	}

	@Override
	public String toString() {
		return this.entityClass.getSimpleName();
	}

	public Class<? extends BaseEntity> getEntityClass() {
		return entityClass;
	}

	public Class<? extends RequestController> getEntityControllerClass() {
		return entityControllerClass;
	};
}
