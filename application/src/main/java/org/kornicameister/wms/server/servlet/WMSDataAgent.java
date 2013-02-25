package org.kornicameister.wms.server.servlet;

import org.apache.log4j.Logger;
import org.kornicameister.wms.cm.CRUD;
import org.kornicameister.wms.cm.impl.RequestController;
import org.kornicameister.wms.server.extractor.RDExtractor;
import org.kornicameister.wms.utilities.hibernate.HibernateBridge;
import org.kornicameister.wms.utilities.hibernate.HibernateBridgeException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * {@link WMSDataAgent} acts as a middle-man between server and client in
 * process of initializing the client.
 * <p/>
 * This particular servlet must connect itself to database, select all required
 * information, transform them into valid JSON form and return to the client.
 */
@WebServlet(
        name = "WMS Data Agent",
        description = "Dispatcher for all data related requests",
        urlPatterns = {
                "/wms/agent/*"
        },
        loadOnStartup = 1
)
public class WMSDataAgent extends HttpServlet {
    private static final long serialVersionUID = -217845239414591742L;
    private static Logger logger = Logger.getLogger(WMSDataAgent.class
            .getName());

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            if (HibernateBridge.accessHibernate()) {
                logger.info(String.format(
                        "Hibernate connection is up and running, status = %s",
                        !HibernateBridge.getSessionFactory().isClosed())
                );
            } else {
                logger.error("No exception was caught, still connection is down");
            }
        } catch (HibernateBridgeException e) {
            logger.error("Something went wrong when accessing Hibernate", e);
        }
    }

    @Override
    public void destroy() {
        if (HibernateBridge.closeHibernate()) {
            logger.info("Closed Hibernate connection");
        } else {
            logger.warn("Failed to close Hibernate connection");
        }
        super.destroy();
    }

    private void processRequest(HttpServletRequest req,
                                HttpServletResponse resp, CRUD action) throws IOException {
        logger.info(String.format("Processing request %s", action));
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        RequestController controller;

        if ((controller = RequestController.pickController(RDExtractor.parse(req, action))) == null) {
            logger.warn(String.format("Module not recognized, tried extract from URI=[%s]", req.getRequestURI()));
            out.write(RequestController.buildErrorResponse());
        } else {
            out.write(controller.process());
        }

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
