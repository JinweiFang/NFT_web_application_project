package Controller;

import Domain.User;
import Service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class profileServlet extends HttpServlet {
    private UserService userService;
    private static String HOST = "http://localhost:8080/";
    private static String AUTHENTICATE = "authenticateServlet";

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

        RequestDispatcher dispatcher = req.getRequestDispatcher(req.getContextPath() + "WEB-INF/View/account/profile.jsp");
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

        // Handle url routing
        // for example: [currentServlet]/someurl/..
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            // Remove the first character and then split the url /hello/world -> [hello, world]
            String urls[] = req.getPathInfo().substring(1).split("/");

            if(urls[0].equals("profileImageUpdate")) {
                profileChangeUpdate(req, resp);
            } else if (urls[0].equals("changePassword")) {
                passwordChange(req, resp);
            } else if (urls[0].equals("changePersonalInfo")) {
                personalInformationChange(req, resp);
            } else if (urls[0].equals("deleteAccount")) {
                deleteAccount(req, resp);
            }
        }
    }

    private void profileChangeUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("profileImage");
        String fileName = filePart.getSubmittedFileName();
        for (Part part : req.getParts()) {
            part.write(getServletContext().getRealPath("") + fileName);
        }
        resp.getWriter().print("The file uploaded successfully.");
        return;
    }

    private void passwordChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User loggedUser = (User) req.getSession().getAttribute("user");

        if(loggedUser != null) {

            // check that the oldPassword is correct
            User response = userService.authenticateUser(loggedUser.getUsername(), req.getParameter("oldPassword"));

            if(response != null && userService.updateUserPassword(loggedUser.getUsername(), req.getParameter("newPassword"))) {
                resp.sendRedirect("/profile?errmsg=4");
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/profile?errmsg=1");
    }

    private void personalInformationChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User loggedUser = (User) req.getSession().getAttribute("user");

        if (loggedUser != null) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String fName = req.getParameter("fName");
            String lName = req.getParameter("lName");

            if (userService.updatePersonalInfo(loggedUser.getId(), fName, lName, email, username)) {
                // The database was updated, now we need to update the session to reflect the right info
                loggedUser.setUsername(username);
                loggedUser.setEmail(email);
                loggedUser.setfName(fName);
                loggedUser.setlName(lName);

                // Update this user's info
                resp.sendRedirect(req.getContextPath() + "/profile?errmsg=3");
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/profile?errmsg=1");
    }

    private void deleteAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User loggedUser = (User) req.getSession().getAttribute("user");

        if (loggedUser != null) {
            boolean deletionSuccess = userService.deleteUserById(String.valueOf(loggedUser.getId()));
            if (deletionSuccess) {
                // this will automatically invalidate the session
                resp.sendRedirect(req.getContextPath() + "/authenticate/logout");
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/profile?errmsg=1");
    }
}