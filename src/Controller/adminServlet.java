package Controller;

import Service.AccountManagementService;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class adminServlet extends HttpServlet {
    private AccountManagementService accountManagementService;

    @Override
    public void init() throws ServletException {
        this.accountManagementService = new AccountManagementService(new UserService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Must be admin to access this page
        if(!accountManagementService.authAdmin(req)) {
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
                req.setAttribute("users", accountManagementService.getUserList());
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
        if(!accountManagementService.authAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character("/") and then split the url
            // For example: /[servlet]/hello/world/ -> [hello, world, ]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Route -> /admin/register
            if(urls[0].equals("register")) accountManagementService.handleRegistration(req, resp);
            // Route -> /admin/update
            else if(urls[0].equals("update")) accountManagementService.handleUpdate(req, resp);
            // Route -> /admin/delete
            else if(urls[0].equals("delete")) accountManagementService.handleDeletion(req, resp);
        }
    }
}
