package Data;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dataSource {
    private final Connection conn;
    private final String SQLITE_URL = getSqlitePath();

    public dataSource() {
        this.conn = SQLiteDB(); // default
    }

    public dataSource(String dbname) {
        switch (dbname.toLowerCase()) {
            case "postgres" -> this.conn = PostgresDB();
            default -> this.conn = SQLiteDB();
        }
    }

    private Connection SQLiteDB() {
        Connection conn = null;

        try {
            // Need it for recognizing SQLite dependency
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(SQLITE_URL);

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
            // Need it for recognizing Postgresql dependency
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(url, user, System.getenv("postgresql_pass"));
            System.out.println("Connected to the PostgreSQL databse successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * Returns a valid path to the Sqlite database file in the project.
     */
    private String getSqlitePath() {
        URL resource = getClass().getResource("/");
        String absPath = resource.getPath().replace("/out/artifacts/test_war_exploded/WEB-INF/classes/", "/src/database").replaceAll("%20", " ");
        absPath = java.net.URLDecoder.decode(absPath, StandardCharsets.UTF_8);

        StringBuilder path = new StringBuilder();
        path.append("jdbc:sqlite:");
        path.append(absPath);

        return path.toString();
    }

    public Connection getConn() {
        return this.conn;
    }
}
