package Data;

import Model.Test;

import java.sql.*;
import java.util.ArrayList;

public class TestContext implements iTest {
    private Connection conn;

    public TestContext(Connection _conn) {
        this.conn = _conn;
    }

    @Override
    public Test[] getAllNames() {
        ArrayList<Test> result = new ArrayList<>();
        String sql = "SELECT ID, name, created_on FROM test";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result.add(new Test(rs.getInt(1), rs.getString(2), rs.getLong(3)));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Test[0]);
    }

    @Override
    public Test getName(int ID) {
        Test result = null;
        String sql = "SELECT ID, name, created_on FROM test WHERE ID = ? ";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                result = new Test(rs.getInt(1), rs.getString(2), rs.getLong(3));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public Test getName(String name) {
        Test result = null;
        String sql = "SELECT ID, name, created_on FROM test WHERE name = ? ";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                result = new Test(rs.getInt(1), rs.getString(2), rs.getLong(3));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
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
