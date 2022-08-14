package Service;

import Data.dao.userDao;
import Data.dataSource;
import Data.dao.impl.userContext;
import Domain.User;

import java.util.regex.Pattern;

public class UserService {
    private userDao repo;

    public UserService() {
        this.repo = new userContext(new dataSource().getConn());
    }

    public User authenticateUser(String username, String password) {
        // Sanitize input before pass it for db
        username = username.trim();
        password = password.trim();

        // Make sure the input is proper
        if (!username.isBlank() && !password.isBlank()) {
            // Check if the user exists in the database
            User usr = new User();
            usr.setUsername(username);
            usr.setPassword(password);

            User response = repo.find(usr);

            if (response != null) return response;
        }

        return null;
    }

}
