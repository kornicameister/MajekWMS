package wms.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
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
import wms.model.hibernate.UnitType;
import wms.model.hibernate.Warehouse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@link WMSInitAgent} acts as a middle-man between server and client in
 * process of inititializing the client.
 * 
 * This particular servlet must connect itself to database, select all required
 * information, transform them into valid JSON form and return to the client.
 */
@WebServlet(urlPatterns = { "/wms/start/*" }, asyncSupported = true, loadOnStartup = 1, name = "WMSInitAgent")
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

	/**
	 * Utility method that allows to obtain CRUD action, as the last part of the
	 * request's URI.
	 * 
	 * @param request
	 * @return a CRUD action description
	 */
	private String getCRUDAction(HttpServletRequest request) {
		String uri[] = request.getRequestURI().split("/");
		String action = uri[uri.length - 1];
		return action;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = getCRUDAction(request);
		PrintWriter out = response.getWriter();

		logger.info(String.format("Request [ %s ]  detected, processing...",
				action));
		if (action.equals("read")) {
			Map<String, String[]> params = request.getParameterMap();

			GInitParameters init = this.loadRequired(params.get("requires"));
			init.getConfiguration().setGetID(params.get("_dc")[0]);
			out.write(init.toGsonString());
		} else {
			logger.warning(String.format(
					"Unknown request GET[ %s ] for the servlet", action));
			out.write("{success: false, message: 'Unknow request " + action
					+ "'}");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = getCRUDAction(req);
		PrintWriter out = resp.getWriter();

		logger.info(String.format("Request [ %s ]  detected, processing...",
				action));
		if (action.equals("update")) {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;
			try {
				InputStream inputStream = req.getInputStream();
				if (inputStream != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(
							inputStream));
					char[] charBuffer = new char[128];
					int bytesRead = -1;
					while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
						stringBuilder.append(charBuffer, 0, bytesRead);
					}
				} else {
					stringBuilder.append("");
				}
			} catch (IOException ex) {
				throw ex;
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						throw ex;
					}
				}
			}
			Gson gson = new GsonBuilder().setDateFormat("m/d/y").create();
			Warehouse newWarehouse = (Warehouse) gson
					.fromJson(stringBuilder.toString(), GInitParameters.class)
					.getConfiguration().getWarehouses().toArray()[0];
			Serializable newWarehouseId = null;

			Session session = this.sessionFactory.openSession();
			session.beginTransaction();
			try {
				if (newWarehouse != null) {
					newWarehouseId = session.save(newWarehouse);
				}
				session.getTransaction().commit();
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						String.format("Error when inserting warehouse..."), e);
				session.getTransaction().rollback();
				out.write("{success: failure, message: " + e.getMessage() + "}");
			}

			out.write("{success: true, warehouseId: " + newWarehouseId + "}");
		} else {
			logger.warning(String.format(
					"Unknown request PUT[ %s ] for the servlet", action));
			out.write("{success: false, message: 'Unknow request " + action
					+ "'}");
		}
	}

	private GInitParameters loadRequired(String[] param) {
		GInitParameters initParameters = new GInitParameters();
		for (String p : param[0].split(",")) {
			if (p.equals("warehouses")) {
				initParameters.getConfiguration().setWarehouses(
						new HashSet<Warehouse>(this.readWarehouse()));
			} else if (p.equals("unitTypes")) {
				initParameters.getConfiguration().setUnitTypes(
						new HashSet<UnitType>(this.readUnitTypes()));
			} else {
				logger.warning(String.format(
						"Unknow required property key [ %s ]", p));
				return initParameters;
			}
			logger.info(String.format(
					"Request for [ %s ], database query finished with success",
					p));
		}

		logger.log(Level.INFO, "Required properties count = {0}", param.length);
		return initParameters;
	}

	@SuppressWarnings("unchecked")
	private List<UnitType> readUnitTypes() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<UnitType> result = session.createQuery("from UnitType").list();
		session.getTransaction().commit();

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Warehouse> readWarehouse() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<Warehouse> result = session.createQuery("from Warehouse").list();
		session.getTransaction().commit();

		return result;
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
