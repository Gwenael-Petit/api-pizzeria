package controlleurs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MyServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(req.getMethod().equalsIgnoreCase("PATCH")) {
			doPatch(req, res);
		} else {
			super.service(req, res);
		}
	}
	
	public abstract void doPatch(HttpServletRequest req, HttpServletResponse res);

}
