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
        // Handle url routing
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            if (urls[0].equals("logout")) handleLogout(req, resp);
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
            if (urls[0].equals("login")) handleLogin(req, resp);
            // Route -> authenticate/reset
            else if (urls[0].equals("reset")) handleReset(req, resp);
            // Route -> authenticate/security-questions
            else if (urls[0].equals("security-questions")) handleResetViaSecurityQuestions(req, resp);
            // Route -> authenticate/new-password
            else if (urls[0].equals("new-password")) handleNewPassword(req, resp);
            // Route -> authenticate/register
            else if (urls[0].equals("register")) handleRegistration(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }

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
            
            return;
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
        if(userService.registerUser(req.getParameter("fName"), req.getParameter("lName"), req.getParameter("email"),
                req.getParameter("username"), req.getParameter("password"), req.getParameter("secAns1"),
                req.getParameter("secAns2"), req.getParameter("secAns3"))) {
            resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?succsignup=1");
            return;
        }
        // Redirect back to signup page if signup failed
        resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?errmsg=1");
    }

    private void handleResetViaSecurityQuestions(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User usrResponse = userService.findUserByUsername(req.getParameter("username"));

        if (usrResponse == null) {  // User not found
            resp.sendRedirect(req.getContextPath() + "/account/security-questions.jsp?errmsg=4");
            return;
        }

        boolean[] correct = userService.checkSecurityAnswers(req.getParameter("username"), req.getParameter("secAns1"), req.getParameter("secAns2"), req.getParameter("secAns3"));

        String redirect = req.getContextPath() + "/account/security-questions.jsp?";
        if(correct[0] && correct[1] && correct[2]){
            boolean successfulReset = userService.updateUserPassword(req.getParameter("username"), req.getParameter("password"));
            if(successfulReset) {
                redirect += "succreset=1";
            } else {
                redirect += "errmsg=3";
            }
        } else {
            redirect += "errmsg=2";

            if(!correct[0]) redirect += "&badAns1=1";
            if(!correct[1]) redirect += "&badAns2=1";
            if(!correct[2]) redirect += "&badAns3=1";
        }

        resp.sendRedirect(redirect);
    }
}
