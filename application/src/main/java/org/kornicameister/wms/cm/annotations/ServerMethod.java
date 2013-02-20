package org.kornicameister.wms.cm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author kornicameister
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServerMethod {
    String mapping();

    RestMethod method();
}
