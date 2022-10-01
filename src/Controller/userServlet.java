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

public class userServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Handle unauthorized access (must be logged in to access this area)
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url
            // For example: /@servlet/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Route -> /u/@username
            if(urls[0].startsWith("@")) {
                User found = userService.findUserByUsername(urls[0].substring(1));

                if (found != null && found.getUsername().equals(loggedUser.getUsername())) displayMessage(req, resp, "Display user profile for @" + found.getUsername());
                else displayMessage(req, resp, "User could not be found!");
            }
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 Error
    }

    private void displayMessage(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + "/WEB-INF/View/display-message.jsp?msg=" + msg);
        dispatcher.forward(req, resp);
    }
}
