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

import wms.controller.UnitController;
import wms.controller.UnitTypeController;
import wms.controller.WarehouseController;
import wms.controller.base.CRUD;
import wms.controller.base.RequestController;
import wms.controller.base.extractor.RDExtractor;
import wms.controller.hibernate.HibernateBridge;
import wms.controller.hibernate.HibernateBridgeException;

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

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response, CRUD action) throws IOException {
		String uri[] = request.getRequestURI().split("/");
		String module = RDExtractor.getModuleAction(uri);
		Map<String, String[]> params = RDExtractor.getParameter(request);
		String payload = RDExtractor.getPayload(request);
		RequestController controller = null;
		PrintWriter out = response.getWriter();

		if (module.equals("warehouse")) {
			controller = new WarehouseController(action, params, payload);
		} else if (module.equals("unittype")) {
			controller = new UnitTypeController(action, params, payload);
		} else if (module.equals("unit")) {
			controller = new UnitController(action, params, payload);
		} else {
			logger.warning(String.format("Unrecognized module [ %s ]", module));
		}

		controller.process();
		out.write(controller.buildResponse());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response, CRUD.READ);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response, CRUD.CREATE);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.processRequest(req, resp, CRUD.UPDATE);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.processRequest(req, resp, CRUD.DELETE);
	}

}
