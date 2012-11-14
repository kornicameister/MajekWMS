package wms.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class WMSIndexController
 */
public class WMSController extends HttpServlet {
    private static final long serialVersionUID = 7938319981003758746L;
    private static Logger log = Logger.getLogger(WMSController.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.config(String.format(
                "Controller ready to be used, init value = %d",
                config.getInitParameter("page")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String pageReference = req.getParameter("page");
        if (pageReference != null) {
            if (pageReference.isEmpty()) {
                log.warning("<page> param is empty");
            } else {
                log.log(Level.INFO, "ResponseReadFormat for {0} page", pageReference);
            }

            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.write("ResponseReadFormat for " + pageReference);
        } else {
            log.severe("<page> is null param within request body");
        }
    }
}
