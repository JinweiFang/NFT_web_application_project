package Data;

import java.sql.Connection;

public abstract class abstractConnect {
    protected final Connection conn;

    protected abstractConnect(Connection conn) {
        this.conn = conn;
    }

    protected Connection getConn() {
        return this.conn;
    }
}
