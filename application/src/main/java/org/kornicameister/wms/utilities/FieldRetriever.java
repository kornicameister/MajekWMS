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

    public static Field findField(Object source, String fieldName) {
        return FieldRetrieverMethods.findField(source.getClass(), fieldName, 0);
    }

    private static class FieldRetrieverMethods {
        public static Field findField(Class clazz, final String fieldName, int depth) {
            Field field = null;
            final String simpleName = clazz.getSimpleName();
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
