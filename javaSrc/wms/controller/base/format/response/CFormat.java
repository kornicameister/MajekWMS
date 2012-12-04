package wms.controller.base.format.response;

import wms.controller.base.CRUD;
import wms.controller.base.format.BaseFormat;
import wms.model.BasicPersistentObject;

import java.util.Set;

public class CFormat extends BaseFormat {

    public CFormat(
            boolean success,
            Long time,
            String handler,
            String entity,
            Set<BasicPersistentObject> affected) {
        super(true, time, handler, entity, affected, CRUD.CREATE);
    }
}
