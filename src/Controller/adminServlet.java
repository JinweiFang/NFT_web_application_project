package Controller;

import Domain.User;
import Service.AccountManagement;
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
    private AccountManagement accountManagement;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        this.accountManagement = new AccountManagement(this.userService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Must be admin to access this page
        if(!accountManagement.authAdmin(req)) {
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
                req.setAttribute("users", userService.getAllUsers());
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
        if(!accountManagement.authAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character("/") and then split the url
            // For example: /[servlet]/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Route -> /admin/register
            if(urls[0].equals("register")) accountManagement.handleRegistration(req, resp);
            // Route -> /admin/update
            else if(urls[0].equals("update")) accountManagement.handleUpdate(req, resp);
            // Route -> /admin/delete
            else if(urls[0].equals("delete")) accountManagement.handleDeletion(req, resp);
        }
    }
}
