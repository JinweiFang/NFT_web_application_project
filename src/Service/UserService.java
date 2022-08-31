package Service;

import Data.dao.TokenDao;
import Data.dao.impl.tokenContext;
import Data.dao.userDao;
import Data.dataSource;
import Data.dao.impl.userContext;
import Domain.Token;
import Domain.User;

import java.util.List;

import static Utils.DateUtils.generateUnixTimestamp;
import static Utils.TokenUtils.generatePasswordResetToken;

public class UserService {
    private userDao userRepo;
    private TokenDao tokenRepo;

    public UserService() {
        dataSource ds = new dataSource();
        this.userRepo = new userContext(ds.getConn());
        this.tokenRepo = new tokenContext(ds.getConn());
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

            User response = userRepo.find(usr);

            if (response != null) return response;
        }

        return null;
    }

    public List<User> getAllUsers(){
        return (List<User>) userRepo.findAll();
    }

    public User findUserByUsername(String username) {
        username = username.trim();

        // Make sure the input is proper
        if (!username.isBlank()) {
            User usr = new User();
            usr.setUsername(username);

            User response = userRepo.findByUsername(usr);

            if (response != null) return response;
        }

        return null;
    }

    public boolean updateUserPassword(String username, String password) {
        // Sanitize input before pass it for db
        username = username.trim();
        password = password.trim();

        // Make sure the input is proper
        if (!username.isBlank() && !password.isBlank()) {
            User usr = new User();
            usr.setUsername(username);
            usr.setPassword(password);

            User response = userRepo.updateByUsername(usr);

            if (response != null) return true;
        }

        return false;
    }

    public Token createPasswordResetTokenForUser(String username) {
        // Sanitize input
        username = username.trim();

        // Make sure the input is proper
        if (!username.isBlank()) {
            Token tkn = new Token();
            tkn.setUsername(username);
            tkn.setTokenValue(generatePasswordResetToken());
            tkn.setToken_type(0); // type 0 -> password reset
            tkn.setExpiration_date(generateUnixTimestamp(10));

            Token response = tokenRepo.save(tkn);

            if (response != null) return response;
        }

        return null;
    }

    public boolean varifyPasswordResetToken(String username, String token) {
        // Sanitize input
        username = username == null ? "" : username.trim();
        token = token == null ? "" : token.trim();

        // Make sure the input is proper
        if (!username.isBlank() && !token.isBlank()) {
            Token tkn = new Token();
            tkn.setUsername(username);
            tkn.setTokenValue(token);

            Token response = tokenRepo.find(tkn);

            // if an item is found
            if (response != null) {
                long currentUnixTime = generateUnixTimestamp();
                long expirationDate = response.getExpiration_date();

                if (expirationDate > currentUnixTime) return true;
            }
        }

        return false;
    }

    public boolean registerUser(String fName, String lName, String email, String username, String password){
        // Sanitize input before pass it for db
        fName = fName.trim();
        lName = lName.trim();
        email = email.trim();
        username = username.trim();
        password = password.trim();

        // Make sure the input is proper
        if (!fName.isBlank() && !lName.isBlank() && !email.isBlank() && !username.isBlank() && !password.isBlank()) {
            // If user already exists, don't attempt to save new user
            if (findUserByUsername(username) != null) return false;

            // Create and save new user
            User usr = new User();
            usr.setfName(fName);
            usr.setlName(lName);
            usr.setEmail(email);
            usr.setUsername(username);
            usr.setPassword(password);
            if (userRepo.save(usr) != null) return true;
        }

        return false;
    }
}
