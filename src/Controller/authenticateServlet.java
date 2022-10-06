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
            // Route -> authenticate/register@admin
            else if (urls[0].equals("register@admin")) handleRegistrationByAdmin(req, resp);
            // Route -> authenticate/update@admin
            else if (urls[0].equals("update@admin")) handleUpdateByAdmin(req, resp);
            // Route -> authenticate/delete@admin
            else if (urls[0].equals("delete@admin")) handleDeletionByAdmin(req, resp);
        }
    }


    // HELPER METHODS
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



    /*
    Having separate method methods for admin is crucial for security purposes
    It also makes it easier to handle error messages
     */
    private void handleUnauthorizedAccessByNonAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");

        if (loggedUser == null || !loggedUser.isAdmin()) {
            String msg = "Must be an admin!";
            RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + "/WEB-INF/View/display-message.jsp?msg=" + msg);
            dispatcher.forward(req, resp);
            return;
        }
    }

    private void handleRegistrationByAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Handle unauthorized access (must be admin)
        handleUnauthorizedAccessByNonAdmin(req, resp);

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

    private void handleUpdateByAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Handle unauthorized access (must be admin)
        handleUnauthorizedAccessByNonAdmin(req, resp);

        boolean updateSuccess = userService.updateUserById(req.getParameter("id"), req.getParameter("fName"), req.getParameter("lName"),
                req.getParameter("email"), req.getParameter("username"), req.getParameter("password"),
                req.getParameter("accountType"));

        if(updateSuccess) {
            resp.sendRedirect(req.getContextPath() + "/admin/account-list?successmsg=1");
            return;
        }

        // Redirect back to account list if registration failed
        resp.sendRedirect(req.getContextPath() + "/admin/account-list?errmsg=1");
    }

    private void handleDeletionByAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle unauthorized access (must be admin)
        handleUnauthorizedAccessByNonAdmin(req, resp);

        boolean deletionSuccess = userService.deleteUserById(req.getParameter("id"));

        if(deletionSuccess) {
            resp.sendRedirect(req.getContextPath() + "/admin/account-list?successmsg=1");
            return;
        }

        // Redirect back to account list if registration failed
        resp.sendRedirect(req.getContextPath() + "/admin/account-list?errmsg=1");
    }
}
