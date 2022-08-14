package Controller;

import Domain.User;
import Service.UserService;

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
        User response = userService.authenticateUser(req.getParameter("username"), req.getParameter("password"));

        // Redirect back to login page if validation failed
        if (response == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?errmsg=1");
            return;
        }

        // Set up session and redirect to dashboard
        HttpSession session = req.getSession(true);
        session.setAttribute("user", response);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}
