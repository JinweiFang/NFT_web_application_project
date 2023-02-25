package Service;

import Domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccountManagement {
    private UserService userService;

    public AccountManagement(UserService userService){
        this.userService = userService;
    }

    public User getUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session == null) ? null : (User) session.getAttribute("user");
    }
    public void passwordChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User loggedUser = (User) req.getSession().getAttribute("user");

        // make sure logged user can only update their password
        if(loggedUser == null || !loggedUser.getUsername().equals(req.getParameter("username"))) {
            resp.sendRedirect(req.getContextPath() + "/profile?errmsg=1");
        }

        // check that the oldPassword is correct
        User response = userService.authenticateUser(req.getParameter("username"), req.getParameter("oldPassword"));

        // return error if the old password confirmation fails or the update fails
        if(response == null || !userService.updateUserPassword(req.getParameter("username"), req.getParameter("newPassword"))) {
            resp.sendRedirect(req.getContextPath() + "/profile?errmsg=2");
        }
        else {
            resp.sendRedirect("/profile?errmsg=4");
        }
    }

    public void personalInformationChange(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    public void deleteAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User loggedUser = (User) req.getSession().getAttribute("user");

        if (loggedUser != null) {
            boolean deletionSuccess = userService.deleteUserById(String.valueOf(loggedUser.getId()));
            if (deletionSuccess) {
                HttpSession session = req.getSession(false);
                session.invalidate();

                resp.sendRedirect(req.getContextPath() + "/account/login.jsp?succsmsg=1");
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/profile?errmsg=1");
    }
}
