package wms.controller.base;


/**
 * Interface for classes implementing CRUD controllers.
 * All CRUD depended actions must be defined in controllers
 * using this interface.
 * @author  kornicameister
 * @created 01-10-2012
 * @file    Controller.java for project MajekWMS
 * @type    Controller
 *
 */
public interface Controller {
	// CRUD
	void read();
	void update();
	void delete();
	void create();
	
	// RESPONSE
	/**
	 * This method, implemented in classes, builds response that is valid in
	 * context of called CRUD action.
	 * @return
	 */
	String buildResponse();
}
