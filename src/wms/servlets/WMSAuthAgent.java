package wms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import wms.controller.hibernate.HibernateBridge;
import wms.model.User;

import com.google.gson.Gson;

@WebServlet(urlPatterns = { "/wms/auth" }, asyncSupported = true, loadOnStartup = 2, name = "WMSAuthAgent")
public class WMSAuthAgent extends HttpServlet {
	private static final long serialVersionUID = -6718574188511649132L;
	private static Logger logger = Logger.getLogger(WMSAuthAgent.class
			.getName());
	private static boolean READY;

	public WMSAuthAgent() {
		super();
		WMSAuthAgent.READY = false;
	}

	@Override
	public void init() throws ServletException {
		logger.info("Authorization module is up and running");
		WMSAuthAgent.READY = true;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SessionFactory sf = HibernateBridge.getSessionFactory();
		Session session = sf.openSession();
		PrintWriter out = response.getWriter();

		User user = (User) session.byNaturalId(User.class).using("login",
				request.getParameter("login"));
		if (user.getPassword().equals(request.getParameter("password"))) {
			out.write(new Gson().toJson(user).toString());
		} else {
			out.write("{status: invalid}");
		}

		sf.close();
	}

	public static boolean isReady() {
		return READY;
	}

}
