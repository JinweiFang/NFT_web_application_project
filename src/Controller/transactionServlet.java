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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

public class transactionServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Handle cart access
            if (urls[0].equals("cart")) handleCart(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Handle url routing
        // for example: [currentServlet]/someurl/..
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            // Handle cart access
            if (urls[0].equals("cart")) handleCart(req, resp);
        }
    }

    private void handleCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //int[] cart = (int[]) session.getAttribute("cart");
        String button = req.getParameter("remove-button");
        if(button != null){
            removeCartItem(req.getSession(true), Integer.parseInt(button));
        }

        resp.sendRedirect(req.getContextPath() + "/market/cart.jsp");
        return;
    }

    private void removeCartItem(HttpSession session, int itemIndex){
        ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");
        cart.remove(itemIndex);
        session.setAttribute("cart", cart);
    }
}
