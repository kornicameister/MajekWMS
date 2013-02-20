package org.kornicameister.wms.server.extractor;

import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.model.hibernate.BasicPersistentObject;

/**
 * This class represent {@link Entity} independent version
 * that describes which controller and which model will be used.
 */
public class RequestModule {
    final Class<? extends BasicPersistentObject> entityClass;
    final Class<? extends RequestController> entityControllerClass;

    public RequestModule(Class<? extends BasicPersistentObject> entityClass,
                         Class<? extends RequestController> entityControllerClass) {
        this.entityClass = entityClass;
        this.entityControllerClass = entityControllerClass;
    }

    public RequestModule(Entity entity) {
        this.entityClass = entity.getEntityClass();
        this.entityControllerClass = entity.getEntityControllerClass();
    }

    public Class<? extends BasicPersistentObject> getEntityClass() {
        return entityClass;
    }

    public Class<? extends RequestController> getEntityControllerClass() {
        return entityControllerClass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RequestModule");
        sb.append("{entityClass=").append(entityClass);
        sb.append(", entityControllerClass=").append(entityControllerClass);
        sb.append('}');
        return sb.toString();
    }
}
