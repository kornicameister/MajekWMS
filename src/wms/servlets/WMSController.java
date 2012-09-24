package wms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WMSIndexController
 */
@WebServlet(urlPatterns = { "/wms" }, initParams = { @WebInitParam(name = "page", value = "-1") })
public class WMSController extends HttpServlet {
	private static final long serialVersionUID = 7938319981003758746L;
	private static Logger log = Logger.getLogger(WMSController.class.getName());

	public void init(ServletConfig config) throws ServletException {
		log.log(Level.CONFIG, "Controller ready to be used, init value = {0}", config.getInitParameter("page"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String pageReference = req.getParameter("page");
		if (pageReference != null) {
			if(pageReference.isEmpty()){
				log.warning("<page> param is empty");
			}else{
				log.log(Level.INFO, "Request for {0} page", pageReference);
			}

			res.setContentType("application/json");
			PrintWriter out = res.getWriter();
			out.write("Request for " + pageReference);
		} else {
			log.severe("<page> is null param within request body");
		}
	}
}
