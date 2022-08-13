package Controller;

import Data.dao.userDao;
import Data.dataSource;
import Data.userContext;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class loginServlet extends HttpServlet {

    private userDao repo;

    @Override
    public void init() throws ServletException {
        this.repo = new userContext(new dataSource().getConn());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User tempUser = new User();
        tempUser.setUsername(username);
        tempUser.setPassword(password);

        User response = repo.find(tempUser);

        // Redirect back to login page if validation failed
        if(response == null) resp.sendRedirect(req.getContextPath() + "/login.jsp?errmsg=1");

        // Set up session and redirect to dashboard
    }
}
