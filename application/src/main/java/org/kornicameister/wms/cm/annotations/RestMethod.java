package org.kornicameister.wms.cm.annotations;

import org.kornicameister.wms.cm.CRUD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author kornicameister
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RestMethod {
    CRUD rest() default CRUD.READ;
}
