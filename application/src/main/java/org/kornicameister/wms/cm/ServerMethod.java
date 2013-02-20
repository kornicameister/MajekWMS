package org.kornicameister.wms.cm;

import org.apache.log4j.Logger;
import org.kornicameister.wms.cm.resolver.ServerControllerClassResolver;
import org.kornicameister.wms.cm.resolver.ServerControllerDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * This class represents additional layer that allows to write server controllers
 * as ordinary method and then mapped them to appropriate servlets
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class ServerMethod {
    private final static Logger LOGGER = Logger.getLogger(ServerMethod.class);
    private final Map<String, ServerControllerDescriptor> mappedControllers = new HashMap<>();
    private final Properties properties;
    private final String propertiesPath;

    public ServerMethod(String propertiesFile) {
        this.properties = new Properties();
        this.propertiesPath = propertiesFile;
    }

    public final void loadInstances() throws IOException {
        this.loadProperties(this.propertiesPath);
        this.loadControllers();
    }

    private void loadProperties(String propertiesFile) throws IOException {
        LOGGER.info(String.format("Loading properties from path [ %s ]", propertiesFile));
        this.properties.load(new FileInputStream(propertiesFile));
    }

    private void loadControllers() throws NotDirectoryException {
        String controllersPackage = this.assemblePackagePath();
        {
            LOGGER.info(String.format("Resolving initiated, entry path point [ %s ]", controllersPackage));

            Long startTime = System.nanoTime();
            {
                if (!controllersPackage.isEmpty()) {
                    File entryPackage = new File(controllersPackage);
                    if (!entryPackage.isDirectory()) {
                        throw new NotDirectoryException(controllersPackage);
                    }

                    for (ServerControllerDescriptor method : ServerControllerClassResolver.resolve(
                            entryPackage,
                            this.properties.getProperty(PropertyKeyring.CONTROLLER_PACKAGE.getValue()))) {
                        this.mappedControllers.put(method.getUriMapping(), method);
                    }
                } else {
                    LOGGER.warn("Missing entry for controllers location...");
                }
            }
            Long endTime = System.nanoTime() - startTime;

            LOGGER.info(String.format("Resolving initiated, finished with [ %d ms ][ %d controllers ]",
                    TimeUnit.NANOSECONDS.toSeconds(endTime),
                    this.mappedControllers.size()));
        }
    }

    private String assemblePackagePath() {
        StringBuilder coreBuilder = new StringBuilder();
        StringBuilder packagePathBuilder = new StringBuilder();
        StringBuilder prefixBuilder = new StringBuilder();

        String dirtyPrefix = this.properties.getProperty(PropertyKeyring.SRC_PREFIX.getValue());
        for (int i = 0; i < dirtyPrefix.length(); i++) {
            if (dirtyPrefix.charAt(i) != File.separatorChar
                    && (dirtyPrefix.charAt(i) == '\\' || dirtyPrefix.charAt(i) == '/')) {
                prefixBuilder.append(File.separatorChar);
            } else {
                prefixBuilder.append(dirtyPrefix.charAt(i));
            }
        }

        String dirtyPath = this.properties.getProperty(PropertyKeyring.CONTROLLER_PACKAGE.getValue());
        for (int i = 0; i < dirtyPath.length(); i++) {
            if (dirtyPath.charAt(i) == '.') {
                packagePathBuilder.append(File.separatorChar);
            } else {
                packagePathBuilder.append(dirtyPath.charAt(i));
            }
        }

        coreBuilder.append(prefixBuilder.toString());
        coreBuilder.append(packagePathBuilder.toString());
        return coreBuilder.toString();
    }

    public ServerControllerDescriptor get(String uriMapping) {
        return mappedControllers.get(uriMapping);
    }
}
