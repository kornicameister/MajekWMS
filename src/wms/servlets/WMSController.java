package wms.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wms.utilities.WMSResponseUtilities;

/**
 * Servlet implementation class WMSIndexController
 */
@WebServlet(name = "WMSIndexController", asyncSupported = true, urlPatterns = { "/wms" }, loadOnStartup = 1)
public class WMSController extends HttpServlet {
	private static final long serialVersionUID = 7938319981003758746L;
	private static Logger log = Logger.getLogger(WMSController.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String pageReference = req.getParameter("page");
		if (pageReference != null) {
			log.log(Level.INFO, "Request for {0} page", pageReference);

			res.setContentType("application/json");
			WMSResponseUtilities.persistenceResponse(pageReference, res);
		} else {
			log.warning("<page> is null param within request body");
		}
	}
}
