package org.kornicameister.wms.server.servlet;

import org.apache.log4j.Logger;
import org.kornicameister.wms.cm.ServerMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author kornicameister
 * @since 0.0.1
 */
public class WMSDataAgent2 extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(WMSDataAgent2.class);
    private ServerMethod serverMethod;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            final String configurationFile = this.getInitParameter("serverMethods");
            LOGGER.info(String.format("Configuring from %s", configurationFile));

            this.serverMethod = new ServerMethod(configurationFile);
            serverMethod.loadInstances();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
