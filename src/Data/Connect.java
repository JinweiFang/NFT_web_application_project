package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final Connection conn;

    public Connect() {
        this.conn = SQLiteDB(); // default
    }

    public Connect(String dbname) {
        switch (dbname.toLowerCase()) {
            case "postgres":
                this.conn = PostgresDB();
                break;
            default:
                this.conn = SQLiteDB();
        }
    }

    private Connection SQLiteDB() {
        Connection conn = null;

        // Connection Info
        String url = "jdbc:sqlite:/Users/johnpiapian/code/other/NFT_web_project/src/database";

        try {
            // Need it for recognizing SQLite dependency
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the SQLite database successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return conn;
    }

    private Connection PostgresDB() {
        Connection conn = null;

        // Connection Info
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";

        try {
            // Need it for recognizing SQLite dependency
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(url, user, System.getenv("postgresql_pass"));
            System.out.println("Connected to the PostgreSQL databse successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return  conn;
    }

    public Connection getConn() {
        return this.conn;
    }
}
