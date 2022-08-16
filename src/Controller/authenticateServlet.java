package Controller;

import Domain.User;
import Service.UserService;

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
        // Make the url contains subfolder -> [currentServlet]/someurl
        if(req.getPathInfo() != null && req.getPathInfo().length() > 1) {

            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

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
            } else if(urls[0].equals("reset")) {
                System.out.println("Reset password!");
            }

        }

        // Code for when the url is just [currentServlet]
        // ....
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle logout
        if(req.getParameterMap().containsKey("logout") && req.getParameter("logout").equals("1")) {
            HttpSession session = req.getSession(false);
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
