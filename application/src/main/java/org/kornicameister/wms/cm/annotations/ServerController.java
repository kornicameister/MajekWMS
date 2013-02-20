package org.kornicameister.wms.cm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is the annotation used by controllers
 * to mapped them selves as the classes
 * able to be used to handle http
 * requests
 *
 * @author kornicameister
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServerController {
    String mapping();

    Class model();
}
