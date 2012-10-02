package wms.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wms.controller.UnitTypeController;
import wms.controller.WarehouseController;
import wms.controller.base.RequestController;
import wms.controller.base.HibernateBridge;
import wms.controller.base.HibernateBridgeException;
import wms.controller.base.RDExtractor;

/**
 * {@link WMSDataAgent} acts as a middle-man between server and client in
 * process of inititializing the client.
 * 
 * This particular servlet must connect itself to database, select all required
 * information, transform them into valid JSON form and return to the client.
 */
@WebServlet(urlPatterns = { "/wms/agent/*" }, asyncSupported = true, loadOnStartup = 1, name = "WMSDataAgent")
public class WMSDataAgent extends HttpServlet {
	private static final long serialVersionUID = -217845239414591742L;
	private static Logger logger = Logger.getLogger(WMSDataAgent.class
			.getName());

	@Override
	public void init() throws ServletException {
		try {
			if (HibernateBridge.accessHibernate()) {
				logger.config("Hibernate connection is up and running");
			} else {
				logger.severe("No exception was caught, still connection is down");
			}
		} catch (HibernateBridgeException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,
					"Someting went wrong when accessing Hibernate", e);
		}
		super.init();
	}

	@Override
	public void destroy() {
		if (HibernateBridge.closeHibernate()) {
			logger.fine("Closed Hibernate connection");
		} else {
			logger.warning("Failed to close Hibernate connection");
		}
		super.destroy();
	}

	private void handleDoGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String uri[] = request.getRequestURI().split("/");
		String action = RDExtractor.getCRUDAction(uri);
		String module = RDExtractor.getModuleAction(uri);
		Map<String, String[]> params = RDExtractor.getParameter(request);
		RequestController controller = null;
		PrintWriter out = response.getWriter();

		if (module.equals("warehouse")) {
			controller = new WarehouseController(action, params);
		} else if (module.equals("unittype")) {
			controller = new UnitTypeController(action, params);
		}

		controller.process();
		out.write(controller.buildResponse());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.handleDoGet(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = "";
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
			// Gson gson = new GsonBuilder().setDateFormat("m/d/y").create();
			// Warehouse newWarehouse = (Warehouse) gson
			// .fromJson(stringBuilder.toString(), GInitParameters.class)
			// .getConfiguration().getWarehouses().toArray()[0];
			// Serializable newWarehouseId = null;
			//
			// // Session session = this.sessionFactory.openSession();
			// // session.beginTransaction();
			// // try {
			// // if (newWarehouse != null) {
			// // newWarehouseId = session.save(newWarehouse);
			// // }
			// // session.getTransaction().commit();
			// // } catch (Exception e) {
			// // logger.log(Level.SEVERE,
			// // String.format("Error when inserting warehouse..."), e);
			// // session.getTransaction().rollback();
			// // out.write("{success: failure, message: " + e.getMessage() +
			// "}");
			// // }
			//
			// out.write("{success: true, warehouseId: " + newWarehouseId +
			// "}");
		} else {
			logger.warning(String.format(
					"Unknown request PUT[ %s ] for the servlet", action));
			out.write("{success: false, message: 'Unknow request " + action
					+ "'}");
		}
	}

}
