package org.kornicameister.wms.cm.resolver;

import org.kornicameister.wms.cm.ServerControllable;

/**
 * Class that is used by appropriate servlet to
 * pass request to mapped server method.
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class ServerControllerDescriptor {
    private final ServerControllable controller;
    private final Class mappedClazz;
    private final String uriMapping;
    private final String filePath;

    public ServerControllerDescriptor(ServerControllable controller,
                                      Class mappedClazz,
                                      String uriMapping,
                                      String controllerClassPath) {
        this.controller = controller;
        this.mappedClazz = mappedClazz;
        this.uriMapping = uriMapping;
        this.filePath = controllerClassPath;
    }

    public ServerControllable getController() {
        return controller;
    }

    public Class getMappedClazz() {
        return mappedClazz;
    }

    public String getUriMapping() {
        return uriMapping;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerControllerDescriptor)) return false;

        ServerControllerDescriptor method = (ServerControllerDescriptor) o;

        return !(controller != null ? !controller.equals(method.controller) : method.controller != null)
                && !(filePath != null ? !filePath.equals(method.filePath) : method.filePath != null)
                && !(mappedClazz != null ? !mappedClazz.equals(method.mappedClazz) : method.mappedClazz != null)
                && !(uriMapping != null ? !uriMapping.equals(method.uriMapping) : method.uriMapping != null);
    }

    @Override
    public int hashCode() {
        int result = controller != null ? controller.hashCode() : 0;
        result = 31 * result + (mappedClazz != null ? mappedClazz.hashCode() : 0);
        result = 31 * result + (uriMapping != null ? uriMapping.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MappedServerMethod");
        sb.append("{uriMapping='").append(uriMapping).append('\'');
        sb.append(", mappedClazz=").append(mappedClazz);
        sb.append(", controller=").append(controller);
        sb.append('}');
        return sb.toString();
    }
}
