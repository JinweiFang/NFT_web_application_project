package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final Connection conn;

    public Connect() {
        this.conn = init();
    }

    private Connection init() {

        // Need it for recognizing SQLite dependency
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // SQLite connection string
        String url = "jdbc:sqlite:/Users/johnpiapian/code/other/NFT_web_project/src/database";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    public Connection getConn() {
        return this.conn;
    }

}
