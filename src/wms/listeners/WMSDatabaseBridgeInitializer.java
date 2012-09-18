package wms.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import wms.database.WMSDatabaseConnectionManager;

/**
 * Application Lifecycle Listener implementation class WMSDatabaseBridge
 * 
 */
@WebListener
public class WMSDatabaseBridgeInitializer implements ServletContextListener {
	private static Logger log = Logger.getLogger(WMSDatabaseBridgeInitializer.class.getName());
	private WMSDatabaseConnectionManager connectionManager;

	@Resource(
			name = "jdbc/majekwms",
			description = "MySQL resources, data bridge",
			authenticationType = AuthenticationType.CONTAINER,
			shareable = true,
			type = javax.sql.DataSource.class,
			lookup = "jdbc/majekwms"
	)
	private DataSource majekWMSDataSource;
	
	/**
	 * Upon this method calling following operations are performed:
	 * 
	 * <pre>
	 * <ol>
	 * <li>Connection to database is established</li>
	 * <li>Established credentials are saved to allow further access</li>
	 * </ol>
	 * </pre>
	 * 
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent context) {
		// initializing resources.
		this.connectionManager = new WMSDatabaseConnectionManager(this.majekWMSDataSource);
		
		// and loading them.
		WMSDatabaseBridgeInitializer.log.entering(WMSDatabaseBridgeInitializer.class.getSimpleName(), "contextInitialized");
		this.connectionManager.openConnection();
		this.connectionManager.killConnection();
		WMSDatabaseBridgeInitializer.log.exiting(WMSDatabaseBridgeInitializer.class.getSimpleName(), "contextInitialized");
	}

	/**
	 * When this method is called server must terminate existing database
	 * connection.
	 * Id does following:
	 * <ul>
	 * <li>unregister all registered drivers</li>
	 * </ul>
	 * 
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent context) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                log.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }
        }
	}

}
