package Controller;

import Domain.User;

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
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("user");

        // Handle url routing for sub folders
        // Only admin should be able to access
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url dashboard/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            String msg = "Sorry, admin account is needed to access this page!";
            if(!loggedUser.isAdmin()) dispatchToJSP(req, resp, "/WEB-INF/View/display-message.jsp?msg=" + msg);

            if(urls[0].equals("account-list"))
                dispatchToJSP(req, resp, "/WEB-INF/View/account/account-list.jsp");
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
