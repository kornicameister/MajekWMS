package wms.servlet;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.http.HttpServlet;

/**
 * User: kornicameister
 * Date: 02.12.12
 * Time: 23:17
 */
public class WMSLog4JInit extends HttpServlet {
    public void init() {
        String prefix = getServletContext().getRealPath("/");
        String file = getInitParameter("log4j-init-file");
        if (file != null) {
            PropertyConfigurator.configure(prefix + file);
        }
    }
}
