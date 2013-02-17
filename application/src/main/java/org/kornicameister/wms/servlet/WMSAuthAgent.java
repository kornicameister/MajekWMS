package org.kornicameister.wms.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.kornicameister.wms.controller.base.ResponseFormatBody;
import org.kornicameister.wms.model.User;
import org.kornicameister.wms.utilities.hibernate.HibernateBridge;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WMSAuthAgent extends HttpServlet {
    private static final long serialVersionUID = -6718574188511649132L;
    private static Logger logger = Logger.getLogger(WMSAuthAgent.class
            .getName());

    @Override
    public void init() throws ServletException {
        logger.info("Authorization module is up and running");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
        final Long startTime = System.nanoTime();
        String responseJsonString;
        User user;

        if ((user = (User) this.getServletContext().getAttribute("wms_user")) != null) {
            logger.info(String.format("%s was found in the session", user.getLogin()));
            responseJsonString = this.parseAlreadyLogged(startTime, user);
        } else {
            logger.info("Failed to found any user attached to the session");
            responseJsonString = this.parseNotFoundInSession(startTime);
        }

        writer.write(responseJsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        logger.info("Authorization initialized, looking for provided credentials");
        final Long startTime = System.nanoTime();
        final String login = request.getParameter("login"),
                password = request.getParameter("password");
        final PrintWriter writer = response.getWriter();
        String[] uris = request.getRequestURI().split("/");
        String action = uris[uris.length - 1];
        String responseJsonString = "";
        User user;

        if (action.equals("logout")) {
            this.getServletContext().removeAttribute("wms_user");

            if (this.getServletContext().getAttribute("wms_user") == null) {
                responseJsonString = this.parseLogout(startTime);
            } else {
                logger.warn("Failed to logout the user...still in the session");
                responseJsonString = this.parseFailureInLogOut(startTime);
            }
        } else if ((user = (User) this.getServletContext().getAttribute("wms_user")) != null) {
            logger.info(String.format("%s was found in the session", user.getLogin()));
            responseJsonString = this.parseAlreadyLogged(startTime, user);
        } else {
            if (login == null || password == null) {
                logger.error("Either login/password are null");
                request.setAttribute("wms_missing_credentials", "");
            } else {
                final Session session = HibernateBridge.getSessionFactory().openSession();

                user = (User) session
                        .byNaturalId(User.class)
                        .using("login", login)
                        .load();
                if (this.validateUser(user, password)) {
                    logger.info("User's credentials seems to be valid");
                    this.getServletContext().setAttribute("wms_user", user);
                    responseJsonString = this.parseSuccess(startTime, user);
                } else {
                    logger.info(String.format("%s provided bad password", user.getLogin()));
                    request.setAttribute("wms_bad_password", user.getPassword());
                    responseJsonString = this.parseFailure(startTime);
                }

                session.clear();
                session.close();
            }
        }
        writer.write(responseJsonString);
    }

    private String parseFailureInLogOut(Long startTime) {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(null, "Failed to log out the user",
                        false, System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
    }

    private boolean validateUser(User user, String password) {
        return user.getPassword().equals(password);
    }

    private String parseAlreadyLogged(Long startTime, User user) {
        String responseString = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(user, "User retrieved from session",
                        true, System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
        logger.info(String.format("%s is already logged in", user.getLogin()));
        return responseString;
    }

    private String parseLogout(Long startTime) {
        String responseString = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(null, "User successfully logged out...",
                        true, System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
        logger.info("User has been logged out...");
        return responseString;
    }

    private String parseNotFoundInSession(Long startTime) {
        String responseString = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(null, "User not found in the session",
                        false, System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
        logger.info("User is missing and could have been located in the session");
        return responseString;
    }

    private String parseSuccess(Long startTime, User user) {
        String responseString = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(user, "Provided credentials are valid",
                        true, System.nanoTime() - startTime,
                        WMSAuthAgent.class.getName()), AuthRespond.class);
        logger.info("User provided good credentials and will be logged in");
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

    public class AuthRespond extends ResponseFormatBody {
        public AuthRespond(User user, String message, boolean success,
                           Long time, String handler) {
            super(success, time, handler, user);
            this.message = message;
        }
    }

}
