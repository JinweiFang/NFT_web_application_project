package Controller;

import Domain.User;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class dashboardServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Handle unauthorized access
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("user");

        // Handle url routing
        // Only admin should be able to access the following block
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /dashboard/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            String msg = "Sorry, admin account is needed to access this page!";
            if(!loggedUser.isAdmin()) dispatchToJSP(req, resp, "/WEB-INF/View/display-message.jsp?msg=" + msg);
        }

        // Pass the request onto jsp page
        dispatchToJSP(req, resp, "/WEB-INF/View/dashboard.jsp");
    }

    // Helper method to redirect to jsp
    private void dispatchToJSP(HttpServletRequest req, HttpServletResponse res, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + path);
        dispatcher.forward(req, res);
        return; // just in case
    }

}
