package Controller;

import Service.AccountManagementService;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class profileServlet extends HttpServlet {
    private AccountManagementService accountManagement;

    @Override
    public void init() {
        this.accountManagement = new AccountManagementService(new UserService());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle authorized access
        if (accountManagement.getUser(req) == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + "WEB-INF/View/account/profile.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle authorized access
        if (accountManagement.getUser(req) == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        // Handle url routing
        // for example: [currentServlet]/someurl/..
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            if (urls[0].equals("changePassword")) {
                accountManagement.passwordChange(req, resp);
            }

            else if (urls[0].equals("changePersonalInfo")) {
                accountManagement.personalInformationChange(req, resp);
            }

            else if (urls[0].equals("deleteAccount")) {
                accountManagement.deleteAccount(req, resp);
            }
        }
    }

<<<<<<< HEAD
}
