package Data.dao.impl;

import Data.abstractConnect;
import Data.dao.userDao;
import Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class userContext extends abstractConnect implements userDao {

    public userContext(Connection conn) {
        super(conn);
    }

    @Override
    public Collection<User> findAll() {
        String sql = "SELECT id, fname, lname, email, username, balance, isAdmin, securityAns1, securityAns2, securityAns3 FROM users";
        List<User> result = new ArrayList<>();

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            ResultSet res = pstm.executeQuery();

            while (res.next()) {
                User usr = new User();
                usr.setId(res.getInt(1));
                usr.setfName(res.getString(2));
                usr.setlName(res.getString(3));
                usr.setEmail(res.getString(4));
                usr.setUsername(res.getString(5));
                usr.setBalance(res.getDouble(6));
                usr.setIsAdmin(res.getInt(7));
                usr.setSecAnswers(res.getString(8), res.getString(9), res.getString(10));
                result.add(usr);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public User find(User item) {
        String sql = "SELECT id, fname, lname, email, username, password, balance, isAdmin, securityAns1, securityAns2, securityAns3 FROM users WHERE username = ? AND password = ? ";
        User result = null;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setString(1, item.getUsername());
            pstm.setString(2, item.getPassword());
            ResultSet res = pstm.executeQuery();

            if (res.next()) {
                User usr = new User();
                usr.setId(res.getInt(1));
                usr.setfName(res.getString(2));
                usr.setlName(res.getString(3));
                usr.setEmail(res.getString(4));
                usr.setUsername(res.getString(5));
                usr.setPassword(res.getString(6));
                usr.setBalance(res.getDouble(7));
                usr.setIsAdmin(res.getInt(8));
                usr.setSecAnswers(res.getString(8), res.getString(9), res.getString(10));
                result = usr;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public User findByUsername(User item) {
        String sql = "SELECT id, email, username FROM users WHERE username = ?";
        User result = null;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setString(1, item.getUsername());
            ResultSet res = pstm.executeQuery();

            if (res.next()) {
                User usr = new User();
                usr.setId(res.getInt(1));
                usr.setEmail(res.getString(2));
                usr.setUsername(res.getString(3));
                result = usr;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public User save(User item) {
        String sql = "INSERT INTO users(fname, lname, email, username, password, balance, isAdmin, securityAns1, securityAns2, securityAns3) VALUES(?,?,?,?,?,?,?,?,?,?)";
        boolean success = false;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setString(1, item.getfName());
            pstm.setString(2, item.getlName());
            pstm.setString(3, item.getEmail());
            pstm.setString(4, item.getUsername());
            pstm.setString(5, item.getPassword());
            pstm.setDouble(6, item.getBalance());
            pstm.setInt(7, item.getIsAdmin());
            pstm.setString(8, item.getSecAns1());
            pstm.setString(9, item.getSecAns2());
            pstm.setString(10, item.getSecAns3());

            if (pstm.executeUpdate() == 0) throw new SQLException("Insertion failed! no rows affected.");

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next())
                    item.setId(generatedKeys.getInt(1));
                else
                    throw new SQLException("Insertion failed! no rows affected.");
            }
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success ? item : null;
    }

    @Override
    public User update(User item) {
        String sql = "UPDATE users SET fname=?, lname=?, email=?, isadmin=?, username=?, securityAns1=?, securityAns2=?, securityAns3=? WHERE id=?";
        boolean success = false;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setString(1, item.getfName());
            pstm.setString(2, item.getlName());
            pstm.setString(3, item.getEmail());
            pstm.setInt(4, item.getIsAdmin());
            pstm.setString(5, item.getUsername());
            pstm.setString(6, item.getSecAns1());
            pstm.setString(7, item.getSecAns2());
            pstm.setString(8, item.getSecAns3());
            pstm.setInt(9, item.getId());

            if (pstm.executeUpdate() == 0) throw new SQLException("Update failed! no rows affected.");

            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success ? item : null;
    }

    @Override
    public User updatePasswordByUsername(User item) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        boolean success = false;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setString(1, item.getPassword());
            pstm.setString(2, item.getUsername());

            if (pstm.executeUpdate() == 0) throw new SQLException("Update failed! no rows affected.");

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next())
                    item.setId(generatedKeys.getInt(1));
                else
                    throw new SQLException("Update failed! no rows affected.");
            }

            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success ? item : null;
    }

    @Override
    public User updatePersonalInfo(User item) {
        String sql = "UPDATE users SET fname = ?, lname = ?, username = ?, email = ? WHERE id = ?";
        boolean success = false;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setString(1, item.getfName());
            pstm.setString(2, item.getlName());
            pstm.setString(3, item.getUsername());
            pstm.setString(4, item.getEmail());
            pstm.setString(5, String.valueOf(item.getId()));

            if (pstm.executeUpdate() == 0) throw new SQLException("Update failed! no rows affected.");

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next())
                    item.setId(generatedKeys.getInt(1));
                else
                    throw new SQLException("Update failed! no rows affected.");
            }

            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success ? item : null;
    }

    @Override
    public User delete(User item) {
        String sql = "DELETE FROM users WHERE id = ?";
        boolean success = false;

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setInt(1, item.getId());

            if (pstm.executeUpdate() == 0) throw new SQLException("Delete failed! no rows affected.");

            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return (success) ? item : null;
    }

    @Override
    public String getSecurityAnswer(String username, int status) {

        String result = null;
        String target = null;
        if(status == 0) target = "securityAns1";
        else if(status == 1) target = "securityAns2";
        else if(status == 2) target = "securityAns3";
        else return null;

        String sql = "SELECT " + target + " FROM users WHERE username = ?";

        try (PreparedStatement pstm = getConn().prepareStatement(sql)){
            pstm.setString(1, username);
            ResultSet res = pstm.executeQuery();

            if (res.next()) result = res.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
