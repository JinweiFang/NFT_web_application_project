package Controller;

import Domain.Token;
import Domain.User;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class authenticateServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle logout
        if (req.getParameterMap().containsKey("logout") && req.getParameter("logout").equals("1")) {
            HttpSession session = req.getSession(false);
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/");
        }

        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {

            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Handle url routing
        // for example: [currentServlet]/someurl/..
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Handle login
            if (urls[0].equals("login")) handleLogin(req, resp);
            // Handle reset
            else if (urls[0].equals("reset")) handleReset(req, resp);
            // Handle reset new password
            else if (urls[0].equals("new-password")) handleNewPassword(req, resp);
            // Handle new user registration
            else if (urls[0].equals("signup")) handleRegistration(req, resp);
        }
    }


    // HELPER METHODS
    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User response = userService.authenticateUser(req.getParameter("username"), req.getParameter("password"));

        // If login was successful
        if(response != null) {
            // Set up session and redirect to dashboard
            HttpSession session = req.getSession(true);
            session.setAttribute("user", response);
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        // Redirect back to login page if validation failed
        resp.sendRedirect(req.getContextPath() + "/account/login.jsp?errmsg=1");
    }

    private void handleReset(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User usrResponse = userService.findUserByUsername(req.getParameter("username"));
        
        // If username is valid then set a token for password reset
        if (usrResponse != null) {
            Token tknResponse = userService.createPasswordResetTokenForUser(req.getParameter("username"));

            if (tknResponse != null){
                resp.sendRedirect(req.getContextPath() + "/account/reset.jsp?succmsg=1");
                System.out.println("http://localhost:8080/account/new-password.jsp?uname="+tknResponse.getUsername()+"&token="+tknResponse.getTokenValue());
            }
        }

        // Redirect back to login page if validation failed
        resp.sendRedirect(req.getContextPath() + "/account/reset.jsp?errmsg=1");
    }

    private void handleNewPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean passwordResetSuccess = userService.verifyPasswordResetToken(req.getParameter("uname"), req.getParameter("token"));
        if (passwordResetSuccess) {
            if(userService.updateUserPassword(req.getParameter("uname"), req.getParameter("password"))) {
                resp.sendRedirect(req.getContextPath() + "/account/new-password.jsp?succmsg=1");
                return;
            }
        }

        // Unexpected error occurred
        resp.sendRedirect(req.getContextPath() + "/account/new-password.jsp?errmsg=1");
    }

    private void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(userService.registerUser(req.getParameter("fName"), req.getParameter("lName"), req.getParameter("email"), req.getParameter("username"), req.getParameter("password"))) {
            resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?succsignup=1");
            return;
        }
        // Redirect back to signup page if signup failed
        resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?errmsg=1");
    }
}
