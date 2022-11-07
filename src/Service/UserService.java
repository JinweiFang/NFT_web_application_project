package Service;

import Data.dao.TokenDao;
import Data.dao.impl.tokenContext;
import Data.dao.userDao;
import Data.dataSource;
import Data.dao.impl.userContext;
import Domain.Token;
import Domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static Utils.DateUtils.generateUnixTimestamp;
import static Utils.TokenUtils.generatePasswordResetToken;

public class UserService {
    private final userDao userRepo;
    private final TokenDao tokenRepo;

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

            return userRepo.updatePasswordByUsername(usr) != null;
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

            // if token is found then check for token expiration date
            if (response != null) return response.getExpiration_date() > generateUnixTimestamp();
        }

        return false;
    }

    /**
     * Reusable user registration method
     * isAdmin = 0 denotes a non-admin user
     * default balance is 300
     * @param fName
     * @param lName
     * @param email
     * @param username
     * @param password
     * @param balance
     * @param isAdmin
     * @return true or false
     */
    private boolean registerUser(String fName, String lName, String email, String username, String password, double balance, int isAdmin, String secAns1, String secAns2, String secAns3){
        // Sanitize input
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
            usr.setBalance(balance);
            usr.setIsAdmin(isAdmin);
            usr.setSecAnswers(secAns1, secAns2, secAns3);

            System.out.println("Attempting to register!");

            return userRepo.save(usr) != null;
        }

        return false;
    }

    /**
     * Bare minimum registration requirement
     * @param fName
     * @param lName
     * @param email
     * @param username
     * @param password
     * @return
     */
    public boolean registerUser (String fName, String lName, String email, String username, String password){
        return registerUser(fName, lName, email, username, password, 0, 0, "", "", "");
    }

    /**
     * Registration with security questions, but excluding balance and isAdmin
     * @param fName
     * @param lName
     * @param email
     * @param username
     * @param password
     * @param secAns1
     * @param secAns2
     * @param secAns3
     * @return
     */
    public boolean registerUser (String fName, String lName, String email, String username, String password, String
            secAns1, String secAns2, String secAns3){
        return registerUser(fName, lName, email, username, password, 0, 0, secAns1, secAns2, secAns3);
    }

    /**
     * Registration with balance and isAdmin, but excluding security questions
     * @param fName
     * @param lName
     * @param email
     * @param username
     * @param password
     * @param isAdmin
     * @return
     */
    public boolean registerUser (String fName, String lName, String email, String username, String password, String
            isAdmin){
        double balance = 300;
        int isAdminInt = Integer.parseInt(isAdmin);
        return registerUser(fName, lName, email, username, password, balance, isAdminInt, "", "", "");
    }

    public boolean updateUserById(String id, String fName, String lName, String email, String username, String password, String isAdmin){
        // Sanitize input
        if (!id.isBlank() && !fName.isBlank() && !lName.isBlank() && !email.isBlank() && !username.isBlank() && !isAdmin.isBlank()) {

            User usr = new User();
            usr.setId(Integer.parseInt(id));
            usr.setfName(fName);
            usr.setlName(lName);
            usr.setEmail(email);
            usr.setUsername(username);
            usr.setIsAdmin(Integer.parseInt(isAdmin));

            usr = userRepo.update(usr); // if unsuccessful returns null

            // !password may not always be updated
            if (!password.isBlank() && usr != null) return updateUserPassword(usr.getUsername(), password);

            return usr != null;
        }

        return false;
    }

    public boolean updatePersonalInfo(int id, String fName, String lName, String email, String username) {
        // Sanitize input
        if (!fName.isBlank() && !lName.isBlank() && !email.isBlank() && !username.isBlank()) {
            // Sanitize input sure the input is proper
            User tempUser = new User();
            tempUser.setUsername(username);
            tempUser.setEmail(email);
            tempUser.setfName(fName);
            tempUser.setlName(lName);
            tempUser.setId(id);

            if (userRepo.updatePersonalInfo(tempUser) != null) {

                return true;
            }
        }
        return false;
    }
    public boolean deleteUserById (String id) {
        // Sanitize input
        if (!id.isBlank()) {
            User usr = new User();
            usr.setId(Integer.parseInt(id));
            return userRepo.delete(usr) != null;
        }

        return false;
    }

    public boolean[] checkSecurityAnswers (String username, String ans1, String ans2, String ans3){
        String correctAnswer1 = userRepo.getSecurityAnswer(username, 0);
        String correctAnswer2 = userRepo.getSecurityAnswer(username, 1);
        String correctAnswer3 = userRepo.getSecurityAnswer(username, 2);

        boolean[] correct = {ans1.equals(correctAnswer1), ans2.equals(correctAnswer2), ans3.equals(correctAnswer3)};
        return correct;
    }
}
