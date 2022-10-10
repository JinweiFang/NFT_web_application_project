package Data.dao.impl;

import Data.abstractConnect;
import Data.dao.TokenDao;
import Domain.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class tokenContext extends abstractConnect implements TokenDao {

    public tokenContext(Connection conn) {
        super(conn);
    }

    @Override
    public Collection<Token> findAll() {
        return null;
    }

    @Override
    public Token find(Token item) {
        String sql = "SELECT id, username, token_value, token_type, expiration_date FROM tokens WHERE username = ? AND token_value = ?";
        Token result = null;

        try {
            PreparedStatement pstm = getConn().prepareStatement(sql);
            pstm.setString(1, item.getUsername());
            pstm.setString(2, item.getTokenValue());
            ResultSet res = pstm.executeQuery();

            if (res.next()) {
                Token tkn = new Token();
                tkn.setId(res.getInt(1));
                tkn.setUsername(res.getString(2));
                tkn.setTokenValue(res.getString(3));
                tkn.setToken_type(res.getInt(4));
                tkn.setExpiration_date(res.getInt(5));

                result = tkn;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public Token save(Token item) {
        String sql = "INSERT INTO tokens(username, token_value, token_type, expiration_date) VALUES(?,?,?,?)";
        boolean success = false;

        try {
            PreparedStatement pstmt = getConn().prepareStatement(sql);
            pstmt.setString(1, item.getUsername());
            pstmt.setString(2, item.getTokenValue());
            pstmt.setInt(3, item.getToken_type());
            pstmt.setLong(4, item.getExpiration_date());

            if (pstmt.executeUpdate() == 0) throw new SQLException("Insertion failed! no rows affected.");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next())
                    item.setId(generatedKeys.getInt(1));
                else
                    throw new SQLException("Insertion failed! no rows affected.");
            }

            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success ? item : null;
    }

    @Override
    public Token update(Token item) {
        return null;
    }

    @Override
    public Token delete(Token item) {
        return null;
    }
}
