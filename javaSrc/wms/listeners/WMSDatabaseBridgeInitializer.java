package wms.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * Application Lifecycle Listener implementation class WMSDatabaseBridge
 */
@WebListener
public class WMSDatabaseBridgeInitializer implements ServletContextListener {
    private static Logger log = Logger.getLogger(WMSDatabaseBridgeInitializer.class.getName());

    /**
     * Upon this method calling following operations are performed:
     * <p/>
     * <pre>
     * <ol>
     * <li></li>
     * </ol>
     * </pre>
     *
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent context) {
        WMSDatabaseBridgeInitializer.log.entering(WMSDatabaseBridgeInitializer.class.getSimpleName(), "contextInitialized");

        WMSDatabaseBridgeInitializer.log.exiting(WMSDatabaseBridgeInitializer.class.getSimpleName(), "contextInitialized");
    }

    /**
     * Called upon destroying server connection
     * Id does following:
     * <ul>
     * <li></li>
     * </ul>
     *
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent context) {
    }

}
