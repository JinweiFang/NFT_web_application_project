package Data;

import java.sql.Connection;

public abstract class abstractConnect {
    private final Connection conn;

    protected abstractConnect(Connection conn) {
        this.conn = conn;
    }

    protected Connection getConn() {
        return this.conn;
    }
}
