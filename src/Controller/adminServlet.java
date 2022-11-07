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
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        if(loggedUser == null || !loggedUser.isAdmin()) {
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
                return;
            }
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 Error
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Must be admin to access this page
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        if(loggedUser == null || !loggedUser.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character("/") and then split the url
            // For example: /[servlet]/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Route -> /admin/register
            if(urls[0].equals("register")) handleRegistration(req, resp);
            // Route -> /admin/update
            else if(urls[0].equals("update")) handleUpdate(req, resp);
            // Route -> /admin/delete
            else if(urls[0].equals("delete")) handleDeletion(req, resp);
        }
    }

    private void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean registrationSuccess = userService.registerUser(req.getParameter("fName"), req.getParameter("lName"),
                req.getParameter("email"), req.getParameter("username"), req.getParameter("password"),
                req.getParameter("accountType"));

        if(registrationSuccess) {
            resp.sendRedirect(req.getContextPath() + "/admin/account-list?successmsg=1");
            return;
        }

        // Redirect back to account list if registration failed
        resp.sendRedirect(req.getContextPath() + "/admin/account-list?errmsg=1");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean updateSuccess = userService.updateUserById(req.getParameter("id"), req.getParameter("fName"), req.getParameter("lName"),
                req.getParameter("email"), req.getParameter("username"), req.getParameter("password"),
                req.getParameter("accountType"));

        if(updateSuccess) {
            HttpSession session = req.getSession(false);
            User loggedUser = (session == null) ? null : (User) session.getAttribute("user");

            if (loggedUser.getId() == Integer.parseInt(req.getParameter("id"))) {
                loggedUser.setfName(req.getParameter("fName"));
                loggedUser.setlName(req.getParameter("lName"));
                loggedUser.setEmail(req.getParameter("email"));
                loggedUser.setUsername(req.getParameter("username"));
                loggedUser.setIsAdmin(Integer.parseInt(req.getParameter("accountType")));
                if (!req.getParameter("password").isBlank()) loggedUser.setPassword(req.getParameter("password"));
            }

            resp.sendRedirect(req.getContextPath() + "/admin/account-list?successmsg=1");
            return;
        }

        // Redirect back to account list if registration failed
        resp.sendRedirect(req.getContextPath() + "/admin/account-list?errmsg=1");
    }

    private void handleDeletion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean deletionSuccess = userService.deleteUserById(req.getParameter("id"));

        if(deletionSuccess) {
            resp.sendRedirect(req.getContextPath() + "/admin/account-list?successmsg=1");
            return;
        }

        // Redirect back to account list if registration failed
        resp.sendRedirect(req.getContextPath() + "/admin/account-list?errmsg=1");
    }
}
