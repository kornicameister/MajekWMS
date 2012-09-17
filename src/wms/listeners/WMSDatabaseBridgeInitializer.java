package wms.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class WMSDatabaseBridge
 *
 */
@WebListener
public class WMSDatabaseBridgeInitializer implements ServletContextListener {
	/**
	 * Upon this method calling following operations are performed:
	 * <pre>
	 * <ol>
	 * <li>Connection to database is established</li>
	 * <li>Established credentials are saved to allow further access</li>
	 * </ol>
	 * </pre>
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-gen
    }

	/**
	 * When this method is called server must terminate existing database connection.
	 * 
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
