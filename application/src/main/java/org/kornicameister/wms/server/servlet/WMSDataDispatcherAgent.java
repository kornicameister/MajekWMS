package org.kornicameister.wms.server.servlet;

import org.apache.log4j.Logger;
import org.kornicameister.wms.cm.ServerMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author kornicameister
 * @since 0.0.1
 */
@WebServlet(
        name = "WMS Authorization Agent",
        description = "Dispatcher to handle only request related to login/logout actions",
        urlPatterns = {
                "/wms/data/*"
        },
        loadOnStartup = 1,
        initParams = {
                @WebInitParam(
                        name = "serverMethods",
                        value = "src/main/resources/server_methods.properties"
                )
        }
)
public class WMSDataDispatcherAgent extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(WMSDataDispatcherAgent.class);
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
