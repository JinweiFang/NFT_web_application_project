package Service;

import Domain.Token;
import Domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticateService {

    private UserService userService;

    public AuthenticateService(UserService userService){
        this.userService = userService;
    }

    public void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }

    public void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    public void handleReset(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    public void handleNewPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    public void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(userService.registerUser(req.getParameter("fName"), req.getParameter("lName"), req.getParameter("email"),
                req.getParameter("username"), req.getParameter("password"), req.getParameter("secAns1"),
                req.getParameter("secAns2"), req.getParameter("secAns3"))) {
            resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?succsignup=1");
            return;
        }
        // Redirect back to signup page if signup failed
        resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?errmsg=1");
    }

    public void handleResetViaSecurityQuestions(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
