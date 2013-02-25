package org.kornicameister.wms.cm.resolver;

import org.apache.log4j.Logger;
import org.kornicameister.wms.cm.ServerControllable;
import org.kornicameister.wms.cm.annotations.ServerController;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Resolver that works as resolver for {@link ServerControllable}
 * classes, goes trough given directory or file and performs
 * runtime checkups that makes him sure about the fact
 * that given file contains the file that is really needed
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class ServerControllerClassResolver {
    private final static Logger LOGGER = Logger.getLogger(ServerControllerClassResolver.class);

    /**
     * Method resolving and loading classes for controllers
     *
     * @param file
     * @param controllersPackage
     * @return
     */
    public static Set<ServerControllerDescriptor> resolve(File file, String controllersPackage) {
        Set<ServerControllerDescriptor> methods = new HashSet<>();

        ServerControllerClassResolver.resolveRecursively(file, controllersPackage, methods);

        return methods;
    }

    private static void resolveRecursively(final File file,
                                           final String controllersPackage,
                                           final Set<ServerControllerDescriptor> methods) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileInDir : files) {
                ServerControllerClassResolver.resolveRecursively(fileInDir, controllersPackage, methods);
            }
        } else {
            ServerControllerDescriptor method = null;
            String fileName = controllersPackage + "." + file.getName().split("\\.")[0];

            LOGGER.info(String.format(
                    "Resolving server controller for [ %s ]",
                    fileName
            ));

            try {
                Class classObject = Class.forName(fileName);
                Object checkUpObject = classObject.newInstance();

                if (checkInterface(checkUpObject) && checkAnnotation(checkUpObject)) {

                    ServerController serverController = checkUpObject.getClass().getAnnotation(ServerController.class);
                    ServerControllable serverControllable = (ServerControllable) checkUpObject;

                    method = new ServerControllerDescriptor(serverControllable,
                            serverController.model(),
                            serverController.mapping(),
                            file.getAbsolutePath());

                    LOGGER.info(String.format(
                            "Created new MappedServerMethod object [ %s ]",
                            method.toString()
                    ));
                }
            } catch (ClassNotFoundException exception) {
                LOGGER.warn(String.format(
                        "Error during resolving of server controller for [ %s ]", fileName), exception);
            } catch (InstantiationException instantiationException) {
                LOGGER.warn("Failed to create new instance of controller", instantiationException);
            } catch (IllegalAccessException illegalAccessException) {
                LOGGER.warn("No class property access at this level", illegalAccessException);
            }

            if (method != null) {
                methods.add(method);
            }
        }

    }

    private static boolean checkAnnotation(Object checkUpObject) {
        Annotation annotation = checkUpObject.getClass().getAnnotation(ServerController.class);
        return annotation != null;
    }

    private static boolean checkInterface(Object checkUpObject) {
        Class<?>[] interfaces = checkUpObject.getClass().getInterfaces();
        for (Class<?> interfaced : interfaces) {
            if (interfaced.equals(ServerControllable.class)) {
                return true;
            }
        }
        return false;
    }
}
