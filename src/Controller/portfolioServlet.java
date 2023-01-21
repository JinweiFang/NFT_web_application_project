package Controller;

import Domain.Nft;
import Domain.User;
import Service.NftService;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class portfolioServlet extends HttpServlet {
    private NftService nftService;
    @Override
    public void init() {this.nftService = new NftService();}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle authorized access
        HttpSession session = req.getSession(false);
        User loggedUser = (session == null) ? null : (User) session.getAttribute("user");
        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login.jsp");
            return;
        }

        req.setAttribute("nfts", nftService.getUserNFts(loggedUser.getId()));

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