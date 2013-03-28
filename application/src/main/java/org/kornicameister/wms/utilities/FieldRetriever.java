package org.kornicameister.wms.utilities;

/*
 * User: trebskit
 * Date: 28.03.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Class that wraps retrieving of {@link java.lang.reflect.Field} in
 * recursive function that search for field in following order:
 * <ol>
 * <li>passed class</li>
 * <li>passed class superclass</li>
 * </ol>
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class FieldRetriever {
    private final static Logger LOGGER = Logger.getLogger(FieldRetriever.class);

    /**
     * Recursive called that look for fieldName in passed source.getClass().
     * If property is not there, which means that method reached the stage
     * where type.getClass()==Object.class.
     *
     * @param source    reference to the one class object, where to look property
     * @param fieldName property which is looked for
     * @return valid {@link Field} or null
     */
    public static Field findField(Object source, String fieldName) {
        return FieldRetrieverMethods.findField(source.getClass(), fieldName, 0);
    }

    private static class FieldRetrieverMethods {
        public static Field findField(Class clazz, final String fieldName, int depth) {
            Field field = null;
            final String simpleName = clazz.getSimpleName();
            if (clazz == Object.class) {
                LOGGER.warn(String.format("Object reached in traversing through inheritance tree,\nproperty=%s", fieldName));
                return null;
            }
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Could not locate property=%s in clazz=%s at depth=%s", fieldName, simpleName, depth));
                }
                FieldRetrieverMethods.findField(clazz.getSuperclass(), fieldName, depth + 1);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("Found property=%s for clazz=%s at depth=%s", fieldName, simpleName, depth));
            }
            return field;
        }
    }
}
