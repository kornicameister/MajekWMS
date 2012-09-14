package wms.servlets.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class WMSIndexController
 */
@WebServlet(urlPatterns = { "/wms" }, initParams = { @WebInitParam(name = "page", value = "id", description = "This variable is used in process of internall mapping to DB and further to valid JSON file") })
public class WMSIndexController extends HttpServlet {
	private static final long serialVersionUID = 7938319981003758746L;
	Logger logger = null;

	public WMSIndexController() {
		super();
		Logger tmp = Logger.getRootLogger();
		Logger logger = Logger.getLogger(WMSIndexController.class.getName());
		if(logger != null){
			this.logger = logger;
		}
	}

	public void init(ServletConfig config) throws ServletException {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.logger.info("GET:: param={" + request.getParameter("page") + "}");
	}

}
