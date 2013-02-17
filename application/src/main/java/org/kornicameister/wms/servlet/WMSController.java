package org.kornicameister.wms.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class WMSIndexController
 */
public class WMSController extends HttpServlet {
    private static final long serialVersionUID = 7938319981003758746L;
    private static Logger log = Logger.getLogger(WMSController.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            log.trace(String.format(
                    "Controller ready to be used, init value = %d",
                    config.getInitParameter("page")));
        } catch (RuntimeException e) {
            log.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String pageReference = req.getParameter("page");
        if (pageReference != null) {
            if (pageReference.isEmpty()) {
                log.warn("<page> param is empty");
            } else {
                log.info(String.format("ResponseReadFormat for %s page", pageReference));
            }

            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.write("ResponseReadFormat for " + pageReference);
        } else {
            log.error("<page> is null param within request body");
        }
    }
}
