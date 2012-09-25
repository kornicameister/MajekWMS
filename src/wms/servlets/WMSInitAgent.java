package wms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
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
@WebServlet(urlPatterns = { "/wms/start" }, asyncSupported = false, loadOnStartup = 1)
public class WMSInitAgent extends HttpServlet implements WMSServerClientAgent {
	private static final long serialVersionUID = -217845239414591742L;
	private static Logger logger = Logger.getLogger(WMSInitAgent.class
			.getName());
	private SessionFactory sessionFactory;

	public WMSInitAgent() {
		super();
		this.openHibernateConnection();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.closeHibernateConnection();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> params = request.getParameterMap();

		String _dc = params.get("_dc")[0];
		String requires[] = params.get("requires");
		this.loadRequired(requires);

		PrintWriter out = response.getWriter();
		out.write("{_dc: " + _dc + "}");
	}
	
	private void loadRequired(String[] requires) {
		logger.log(Level.INFO, "Required properties count = {0}",requires.length);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	public void openHibernateConnection() {
		Configuration configuration = new Configuration();
		configuration = configuration.configure(WMSModelConstants.HIBERNATE_CFG
				.toString());

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		this.sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		logger.info(String.format("Session for %s active", this.getClass()
				.getName()));
	}

	@Override
	public void closeHibernateConnection() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
		logger.log(Level.FINE, "Connection closed...");
	}

}
