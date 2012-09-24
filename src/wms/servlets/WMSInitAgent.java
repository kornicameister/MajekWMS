package wms.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import wms.model.WMSModelConstants;

/**
 * {@link WMSInitAgent} acts as a middle-man between server and client in
 * process of inititializing the client.
 * 
 * This particular servlet must connect itself to database, select all required
 * information, transform them into valid JSON form and return to the client.
 */
@WebServlet(urlPatterns = { "/wms/start" }, asyncSupported = true, loadOnStartup = 1)
public class WMSInitAgent extends HttpServlet implements WMSServerClientAgent {
	private static final long serialVersionUID = -217845239414591742L;
	private static Logger logger = Logger.getLogger(WMSInitAgent.class.getName());
	private SessionFactory sessionFactory;

	/**
	 * Dispatches some actions prior to moment when this servlet is called for
	 * the first time
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public WMSInitAgent() {
		super();
		this.openHibernateConnection();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.closeHibernateConnection();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.log(Level.WARNING, WMSInitAgent.class.getName()+ " does not support GET");
		throw new ServletException(WMSInitAgent.class.getName()+ " does not support GET");
	}

	/**
	 * Method handler POST request. Please remember that post carries parameters
	 * that indicates which values client requires;
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requires = request.getParameter("requires");
		if (requires == null) {
			logger.log(Level.CONFIG, "No requires param in POST");
		}
	}

	@Override
	public void openHibernateConnection() {
		Configuration configuration = new Configuration();
		configuration = configuration.configure(WMSModelConstants.HIBERNATE_CFG.toString());
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	public void closeHibernateConnection() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
		logger.log(Level.FINE, "Connection closed...");
	}

}
