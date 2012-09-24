package wms.servlets;

/**
 * This is interface that nearly every servlet/service should implement if it
 * lies between server and client of MajekWMS application. Interface provides
 * all methods allowing to communicate with database engine via hibernate
 * interface.
 * 
 * @author kornicameister
 * @created 24-09-2012
 * @file WMSServerClientAgent.java for project MajekWMS
 * @type WMSServerClientAgent
 * 
 */
public interface WMSServerClientAgent {
	/**
	 * Method opens new Hibernate session
	 */
	public void openHibernateConnection();

	/**
	 * Method closes already open Hibernate session
	 */
	public void closeHibernateConnection();
}
