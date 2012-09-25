package wms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import wms.model.WMSModelConstants;
import wms.model.gson.GInitParameters;
import wms.model.hibernate.Warehouse;

/**
 * {@link WMSInitAgent} acts as a middle-man between server and client in
 * process of inititializing the client.
 * 
 * This particular servlet must connect itself to database, select all required
 * information, transform them into valid JSON form and return to the client.
 */
@WebServlet(urlPatterns = { "/wms/start" }, asyncSupported = true, loadOnStartup = 1, name = "WMSInitAgent")
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

		GInitParameters init = this.loadRequired(params.get("requires"));
		init.setGETId(params.get("_dc")[0]);

		PrintWriter out = response.getWriter();
		out.write(init.toGsonString());
	}
	
	private GInitParameters loadRequired(String[] param) {
		GInitParameters initParameters = new GInitParameters();
		for (String p : param[0].split(",")) {
			if(p.equals("warehouse")){
				initParameters.setWarehouses(this.readWarehouse());
			}
		}
		
		logger.log(Level.INFO, "Required properties count = {0}", param.length);
		return initParameters;
	}

	@SuppressWarnings("unchecked")
	private List<Warehouse> readWarehouse() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Warehouse> result = session.createQuery("from Warehouse").list();
		session.getTransaction().commit();
		
		return result;
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
