package org.kornicameister.wms.server.servlet;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.kornicameister.wms.model.hibernate.User;
import org.kornicameister.wms.server.responses.ResponseFormatBody;
import org.kornicameister.wms.utilities.hibernate.HibernateBridge;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "WMS Authorization Agent",
        description = "Dispatcher to retrieve logged user information",
        urlPatterns = {
                "/wms/auth"
        },
        loadOnStartup = 1
)
public class WMSPrincipalRetriever extends HttpServlet {
    private static final long serialVersionUID = -6718574188511649132L;
    private static Logger logger = Logger.getLogger(WMSPrincipalRetriever.class
            .getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
        final Long startTime = System.nanoTime();
        String responseJsonString;
        User user;
        String userName = req.getUserPrincipal().getName();

        final Session session = HibernateBridge.getSessionFactory().openSession();

        user = (User) session
                .byNaturalId(User.class)
                .using("login", userName)
                .load();

        responseJsonString = this.parseSuccess(startTime, user);

        session.clear();
        session.close();

        writer.write(responseJsonString);
    }

    private String parseSuccess(Long startTime, User user) {
        String responseString = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(new AuthRespond(user, "Logged user name",
                        true, System.nanoTime() - startTime,
                        WMSPrincipalRetriever.class.getName()), AuthRespond.class);
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
