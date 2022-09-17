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
        // Sanitize input
        if (!username.isBlank() && !password.isBlank()) {
            // Check if user exists in the database
            User usr = new User();
            usr.setUsername(username);
            usr.setPassword(password);

            return userRepo.find(usr); // returns null if user is not found
        }

        return null;
    }

    public List<User> getAllUsers(){
        return (List<User>) userRepo.findAll();
    }

    public User findUserByUsername(String username) {
        // Sanitize input
        if (!username.isBlank()) {
            User usr = new User();
            usr.setUsername(username);

            return userRepo.findByUsername(usr); // returns null if user is not found
        }

        return null;
    }

    public boolean updateUserPassword(String username, String password) {
        // Sanitize input sure the input is proper
        if (!username.isBlank() && !password.isBlank()) {
            User usr = new User();
            usr.setUsername(username);
            usr.setPassword(password);

            return userRepo.updateByUsername(usr) != null;
        }

        return false;
    }

    public Token createPasswordResetTokenForUser(String username) {
        // Sanitize input
        if (!username.isBlank()) {
            Token tkn = new Token();
            tkn.setUsername(username);
            tkn.setTokenValue(generatePasswordResetToken());
            tkn.setToken_type(0); // type 0 -> password reset
            tkn.setExpiration_date(generateUnixTimestamp(10));

            return tokenRepo.save(tkn); // returns null if token was not created
        }

        return null;
    }

    public boolean verifyPasswordResetToken(String username, String token) {
        // Sanitize input
        if (!username.isBlank() && !token.isBlank()) {
            Token tkn = new Token();
            tkn.setUsername(username);
            tkn.setTokenValue(token);

            Token response = tokenRepo.find(tkn);

            // if token is found then check for token expiration
            if (response != null) return response.getExpiration_date() > generateUnixTimestamp();
        }

        return false;
    }

    public boolean registerUser(String fName, String lName, String email, String username, String password, String secAns1, String secAns2, String secAns3){
        // Sanitize input
        if (!fName.isBlank() && !lName.isBlank() && !email.isBlank() && !username.isBlank() && !password.isBlank() && !secAns1.isBlank() && !secAns2.isBlank() && !secAns3.isBlank()) {
            // If user already exists, don't attempt to save new user
            if (findUserByUsername(username) != null) return false;

            // Create and save new user
            User usr = new User();
            usr.setfName(fName);
            usr.setlName(lName);
            usr.setEmail(email);
            usr.setUsername(username);
            usr.setPassword(password);
            usr.setSecAnswers(secAns1, secAns2, secAns3);
            if (userRepo.save(usr) != null) return true;
        }

        return false;
    }

    public boolean[] checkSecurityAnswers(String username, String ans1, String ans2, String ans3){
        String correctAnswer1 = userRepo.getSecurityAnswer(username, 0);
        String correctAnswer2 = userRepo.getSecurityAnswer(username, 1);
        String correctAnswer3 = userRepo.getSecurityAnswer(username, 2);

        boolean[] correct = {ans1.equals(correctAnswer1), ans2.equals(correctAnswer2), ans3.equals(correctAnswer3)};
        return correct;
    }

}
