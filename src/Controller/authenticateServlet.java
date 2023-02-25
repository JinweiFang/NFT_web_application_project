package Controller;

import Domain.Token;
import Domain.User;
import Service.UserService;
import Service.AuthenticateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class authenticateServlet extends HttpServlet {

    private UserService userService;
    private AuthenticateService authenticateService;

    @Override
    public void init() {
        this.authenticateService = new AuthenticateService(new UserService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            if (urls[0].equals("logout")) authenticateService.handleLogout(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Handle url routing
        // for example: [servlet]/someurl/..
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Route -> authenticate/login
            if (urls[0].equals("login")) authenticateService.handleLogin(req, resp);
            // Route -> authenticate/reset
            else if (urls[0].equals("reset")) authenticateService.handleReset(req, resp);
            // Route -> authenticate/security-questions
            else if (urls[0].equals("security-questions")) authenticateService.handleResetViaSecurityQuestions(req, resp);
            // Route -> authenticate/new-password
            else if (urls[0].equals("new-password")) authenticateService.handleNewPassword(req, resp);
            // Route -> authenticate/register
            else if (urls[0].equals("register")) authenticateService.handleRegistration(req, resp);
        }
    }


}
