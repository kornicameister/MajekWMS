package wms.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import wms.controller.base.format.BaseFormat;
import wms.model.User;
import wms.utilities.hibernate.HibernateBridge;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WMSAuthAgent extends HttpServlet {
    private static final long serialVersionUID = -6718574188511649132L;
    private static Logger logger = Logger.getLogger(WMSAuthAgent.class
            .getName());

    @Override
    public void init() throws ServletException {
        logger.info("Authorization module is up and running");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        Long startTime = System.nanoTime();
        String responseString = "{}";
        Session session = HibernateBridge.getSessionFactory().openSession();

        logger.info("Authorization initialized, looking for provided credentials");

        final String login = request.getParameter("login"), password = request
                .getParameter("password");

        if (login == null || password == null) {
            logger.error("Either login/password are null");
            responseString = parseFailure(startTime);
            session.close();
        } else {

            User user = (User) session.byNaturalId(User.class)
                    .using("login", login).load();
            try {
                if (user == null || !user.getPassword().equals(password)) {
                    responseString = parseFailure(startTime);
                } else if (user.getPassword().equals(password)) {
                    responseString = parseSuccess(startTime, user);
                }
            } catch (NullPointerException e) {
                logger.warn("Failed to parse authorization request", e);
                responseString = parseFailure(startTime);
            }
            session.clear();
            session.close();
        }

        response.getWriter().write(responseString);
    }

    private String parseSuccess(Long startTime, User user) {
        String responseString = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(user, "Provided credentials are valid",
                        true, System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
        logger.info("User's credentials seems to be valid");
        return responseString;
    }

    private String parseFailure(Long startTime) {
        String responseString = new Gson().toJson(
                new AuthRespond(null, "Invalid user's credentials", false,
                        System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
        logger.warn("Invalid user's credentials");
        return responseString;
    }

    public class AuthRespond extends BaseFormat {
        public AuthRespond(User user, String message, boolean success,
                           Long time, String handler) {
            super(success, time, handler, user);
            this.message = message;
        }
    }

}
