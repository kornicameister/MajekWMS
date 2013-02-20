package org.kornicameister.wms.cm;


/**
 * Interface for classes implementing CRUD controllers.
 * All CRUD depended actions must be defined in controllers
 * using this interface.
 *
 * @author kornicameister
 * @created 01-10-2012
 */
public interface ServerControllable {
    void read();

    void update();

    void delete();

    void create();
}
