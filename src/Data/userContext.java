package Data;

import Data.dao.userDao;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class userContext extends AbstractConnect implements userDao {

    public userContext(Connection conn) {
        super(conn);
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User find(User item) {
        String sql = "SELECT id, fname, lname, username, password FROM users WHERE username = ? AND password = ? ";
        User result = null;

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, item.getUsername());
            pstm.setString(2, item.getPassword());
            ResultSet res = pstm.executeQuery();

            if (res.next()) result = new User(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));

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
