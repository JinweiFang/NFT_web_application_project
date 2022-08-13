package Data;

import Data.dao.iDao;

import java.sql.Connection;

public abstract class AbstractConnect {
    protected final Connection conn;

    protected AbstractConnect(Connection conn) {
        this.conn = conn;
    }

    protected Connection getConn() {
        return this.conn;
    }
}
