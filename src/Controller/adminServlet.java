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

public class adminServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Must be admin to access this page
        if(!isAdmin(req, resp)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character("/") and then split the url
            // For example: /[servlet]/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Route -> /admin/account-list
            if(urls[0].equals("account-list")) {
                List<User> users = userService.getAllUsers();

                req.setAttribute("users", users);
                RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + "/WEB-INF/View/account/account-list.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }

    private boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Handle unauthorized access (must be logged in to access this file)
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        return (loggedUser != null && loggedUser.isAdmin());
    }
}
