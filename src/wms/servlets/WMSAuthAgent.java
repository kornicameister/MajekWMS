package wms.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import wms.controller.base.format.BaseFormat;
import wms.controller.hibernate.HibernateBridge;
import wms.model.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

@WebServlet(urlPatterns = { "/wms/auth" }, asyncSupported = true, loadOnStartup = 2, name = "WMSAuthAgent")
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
		String responseString = null;
		Session session = HibernateBridge.getSessionFactory().openSession();

		logger.info("Authorization initialized, looking for provided credentials");

		synchronized (session) {
			User user = (User) session.byNaturalId(User.class)
					.using("login", request.getParameter("login")).load();
			if (user.getPassword().equals(request.getParameter("password"))) {
				// GsonBuilder ways is required only here, because here...user
				// is defined
				responseString = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation()
						.create()
						.toJson(new AuthRespone(user,
								"Provided credentials are valid", true,
								System.nanoTime() - startTime,
								WMSAuthAgent.class.getName()),
								AuthRespone.class).toString();
				logger.info("User's credentials seems to be valid");
			} else {
				responseString = new Gson().toJson(
						new AuthRespone(null, "Invalid user's credentials",
								false, System.nanoTime() - startTime,
								WMSAuthAgent.class.getName()),
						AuthRespone.class).toString();
				logger.warning("Invalid user's credentials");
			}
			session.clear();
			session.close();
		}
		response.getWriter().write(responseString);
	}

	public class AuthRespone extends BaseFormat {
		@Expose
		private final User user;
		@Expose
		private final String message;

		public AuthRespone(User user, String message, boolean success,
				Long time, String handler) {
			super(success, time, handler);
			this.user = user;
			this.message = message;
		}
	}

}
