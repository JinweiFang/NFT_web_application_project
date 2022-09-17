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
        String sql = "SELECT id, fname, lname, email, username, balance, isAdmin FROM users";
        List<User> result = new ArrayList<>();

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
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
                result.add(usr);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public User find(User item) {
        String sql = "SELECT id, fname, lname, email, username, password, balance, isAdmin FROM users WHERE username = ? AND password = ? ";
        User result = null;

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
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

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
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
        String sql = "INSERT INTO users(fname, lname, email, username, password, securityAns1, securityAns2, securityAns3) VALUES(?,?,?,?,?,?,?,?)";
        boolean success = false;

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getfName());
            pstmt.setString(2, item.getlName());
            pstmt.setString(3, item.getEmail());
            pstmt.setString(4, item.getUsername());
            pstmt.setString(5, item.getPassword());
            pstmt.setString(6, item.getSecAns1());
            pstmt.setString(7, item.getSecAns2());
            pstmt.setString(8, item.getSecAns3());

            if (pstmt.executeUpdate() == 0) throw new SQLException("Insertion failed! no rows affected.");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
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
        return null;
    }

    @Override
    public User updateByUsername(User item) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        boolean success = false;

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getPassword());
            pstmt.setString(2, item.getUsername());

            if (pstmt.executeUpdate() == 0) throw new SQLException("Update failed! no rows affected.");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
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
        return null;
    }


    @Override
    public String getSecurityAnswer(String username, int status){

        String target = null;
        if(status == 0) target = "securityAns1";
        else if(status == 1) target = "securityAns2";
        else if(status == 2) target = "securityAns3";
        else return null;

        String sql = "SELECT " + target + " FROM users WHERE username = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            ResultSet res = pstm.executeQuery();

            if (res.next()) {
                return res.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
