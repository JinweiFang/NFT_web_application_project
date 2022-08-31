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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Handle url routing -> [currentServlet]/someurl
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {

            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Handle login
            if (urls[0].equals("login")) {
                User response = userService.authenticateUser(req.getParameter("username"), req.getParameter("password"));

                // Redirect back to login page if validation failed
                if (response == null) {
                    resp.sendRedirect(req.getContextPath() + "/account/login.jsp?errmsg=1");
                    return;
                }

                // Set up session and redirect to dashboard
                HttpSession session = req.getSession(true);
                session.setAttribute("user", response);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            }
            // Handle reset
            else if (urls[0].equals("reset")) {
                User usrResponse = userService.findUserByUsername(req.getParameter("username"));

                // Redirect back to login page if validation failed
                if (usrResponse == null) {
                    resp.sendRedirect(req.getContextPath() + "/account/reset.jsp?errmsg=1");
                    return;
                }

                // Show a success message
                if (usrResponse != null) {
                    Token tknResponse = userService.createPasswordResetTokenForUser(req.getParameter("username"));

                    if (tknResponse != null){
                        resp.sendRedirect(req.getContextPath() + "/account/reset.jsp?succmsg=1");
                        System.out.println("http://localhost:8080/authenticate/new-password?uname="+tknResponse.getUsername()+"&token="+tknResponse.getTokenValue());
                    }

                }
            }
            // Handle new password set up
            else if (urls[0].equals("new-password")) {
                boolean isValid = userService.varifyPasswordResetToken(req.getParameter("uname"), req.getParameter("token"));
                if (isValid) {
                    if(userService.updateUserPassword(req.getParameter("uname"), req.getParameter("password")))
                        resp.sendRedirect(req.getContextPath() + "/account/login.jsp?succresttpwd=1");
                }
            }
            // Handle signing up new user
            else if (urls[0].equals("signup")) {
                if(userService.registerUser(req.getParameter("fName"), req.getParameter("lName"), req.getParameter("email"), req.getParameter("username"), req.getParameter("password"))) {
                    resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?succsignup=1");
                    return;
                }
                // Redirect back to signup page if signup failed
                resp.sendRedirect(req.getContextPath() + "/account/signup.jsp?errmsg=1");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle logout
        if (req.getParameterMap().containsKey("logout") && req.getParameter("logout").equals("1")) {
            HttpSession session = req.getSession(false);
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/");
        }

        // Handle url routing -> [currentServlet]/someurl
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {

            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Handle new password setup
            if (urls[0].equals("new-password") &&
                    req.getParameterMap().containsKey("uname") &&
                    req.getParameterMap().containsKey("token")) {
                // Pass the request onto jsp page
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/account/new-password.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }
}
