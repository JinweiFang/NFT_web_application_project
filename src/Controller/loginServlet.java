package Controller;

import Data.dao.userDao;
import Data.dataSource;
import Data.userContext;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loginServlet extends HttpServlet {

    private userDao repo;

    @Override
    public void init() throws ServletException {
        this.repo = new userContext(new dataSource().getConn());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();

        User tempUser = new User();
        tempUser.setUsername(username);
        tempUser.setPassword(password);

        User result = repo.find(tempUser);

        // Redirect back to login page if validation failed
        if(result == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?errmsg=1");
            return;
        }

        // Set up session and redirect to dashboard
        HttpSession session = req.getSession(true);
        session.setAttribute("user", new User(result.getId(), result.getfName(), result.getlName(), result.getUsername(), ""));
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}
