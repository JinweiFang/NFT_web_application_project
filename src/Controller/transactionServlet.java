package Controller;

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
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

public class transactionServlet extends HttpServlet {

    private UserService userService;
    private NftService nftService;

    @Override
    public void init() {
        this.userService = new UserService();
        this.nftService = new NftService();
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
        String submitButton = req.getParameter("submit-button");
        if(button != null){
            removeCartItem(req.getSession(true), Integer.parseInt(button));
            resp.sendRedirect(req.getContextPath() + "/market/cart.jsp");
            return;
        }
        HttpSession s = req.getSession(true);

        if(submitButton != null){
            boolean success = attemptTransaction((User) s.getAttribute("user"), (ArrayList<Integer>) s.getAttribute("cart"));
            if(success){
                clearCart(s);
                resp.sendRedirect(req.getContextPath() + "/market/cart.jsp?succmsg=1");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/market/cart.jsp?errmsg=1");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/market/cart.jsp");
        return;
    }

    private void removeCartItem(HttpSession session, int itemIndex){
        ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");
        cart.remove(itemIndex);
        session.setAttribute("cart", cart);
    }

    private void clearCart(HttpSession session){
        session.setAttribute("cart", new ArrayList<Integer>());
    }

    private double sumCartPrices(ArrayList<Integer> cart){
        double total = 0;
        for (Integer id : cart){
            double price = nftService.getNftPrice(id);
            if(price == -1)
                return -1;
            total += price;
        }
        return total;
    }

    private boolean attemptTransaction(User user, ArrayList<Integer> cart){
        String username = user.getUsername();
        double balance = userService.getUserBalance(username);
        double cartTotal = sumCartPrices(cart);
        if(cartTotal == -1)
            return false;
        if(balance >= cartTotal)
            return userService.updateUserBalance(user.getUsername(), balance - cartTotal);
        else
            return false;
    }
}
