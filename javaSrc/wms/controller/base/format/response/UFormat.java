package wms.controller.base.format.response;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.BasicPersistentObject;

import java.util.Set;

public class UFormat extends BaseFormat {

    public UFormat(
            boolean success,
            Long time,
            String handler,
            String entity,
            Set<BasicPersistentObject> affected) {
        super(success, time, handler, entity, affected, CRUD.UPDATE);
    }

}