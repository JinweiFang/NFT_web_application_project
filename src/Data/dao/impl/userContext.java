package Data.dao.impl;

import Data.abstractConnect;
import Data.dao.userDao;
import Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class userContext extends abstractConnect implements userDao {

    public userContext(Connection conn) {
        super(conn);
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User find(User item) {
        String sql = "SELECT id, fname, lname, email, username, password FROM users WHERE username = ? AND password = ? ";
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
                result = usr;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public User save(User item) {
        return null;
    }

    @Override
    public User update(User item) {
        return null;
    }

    @Override
    public User delete(User item) {
        return null;
    }
}
