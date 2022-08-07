package Data;

import Model.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestContext implements iTest {
    private Connection conn;

    public TestContext(Connection _conn) {
        this.conn = _conn;
    }

    @Override
    public Test[] getAllNames() {
        return new Test[0];
    }

    @Override
    public Test getName(int ID) {
        return null;
    }

    @Override
    public void addName(Test item) {
        String sql = "INSERT INTO test(name, created_on) VALUES(?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getCreatedOn());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean removeName(Test item) {
        return false;
    }
}
