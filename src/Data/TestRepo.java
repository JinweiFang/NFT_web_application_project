package Data;

import Data.Repo.iTestRepo;
import Model.Test;

import java.sql.*;
import java.util.ArrayList;

public class TestRepo implements iTestRepo {

    private final Connection conn;

    public TestRepo(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Test[] findAll() {
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

    // Created for re-usability
    private Test find(short type, Object value) {
        Test result = null;
        String sql = (type == 0) ? "SELECT ID, name, created_on FROM test WHERE ID = ? " :
                "SELECT ID, name, created_on FROM test WHERE name = ? ";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // set variable type depending on the type of the value
            if(type == 0) pstmt.setInt(1, (Integer) value);
            else pstmt.setString(1, (String) value);

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
    public Test find(Integer ID) {
        return find((short) 0, ID);
    }

    @Override
    public Test find(String name) {
        return find((short) 1, name);
    }

    @Override
    public Test add(Test item) {
        String sql = "INSERT INTO test(name, created_on) VALUES(?,?)";
        boolean success = false;

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getCreatedOn());
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success ? item : null;
    }

    @Override
    public Test update(Test item) {
        return null;
    }

    @Override
    public Test delete(Integer id) {
        return null;
    }
}
