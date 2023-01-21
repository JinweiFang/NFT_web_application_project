package Controller;

import Domain.User;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class portfolioServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init() {this.userService = new UserService();}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle authorized access
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + "WEB-INF/View/nft/portfolio.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle authorized access
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }
    }
}