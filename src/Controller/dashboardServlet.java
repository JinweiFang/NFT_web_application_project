package Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class dashboardServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // initialize
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Handle unauthorized access
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // Handle logout
        if(req.getParameterMap().containsKey("logout") && req.getParameter("logout").equals("1"))
            this.logout();

        // Pass the request onto jsp page
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
        dispatcher.forward(req, resp);
    }

    private void logout() {
        System.out.println("logging out!");
    }
}
