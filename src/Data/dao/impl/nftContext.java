package Data.dao.impl;

import Data.abstractConnect;
import Data.dao.NftDao;
import Domain.Nft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class nftContext extends abstractConnect implements NftDao {

    public nftContext(Connection conn) {
        super(conn);
    }

    @Override
    public Collection<Nft> findAll() {
        return null;
    }

    @Override
    public Collection<Nft> findNftsByOwnerID(Nft nft) {
        String sql = "SELECT * FROM users WHERE ownerId = ?";
        List<Nft> result = new ArrayList<>();

        try (PreparedStatement pstm = getConn().prepareStatement(sql)) {
            pstm.setInt(1, nft.getOwnerId());
            ResultSet res = pstm.executeQuery();

            while (res.next()) {
                Nft temp = new Nft();
                temp.setId(res.getInt("id"));
                temp.setOwnerId(res.getInt("ownerId"));
                temp.setPrice(res.getDouble("price"));
                temp.setPicture(res.getString("picture"));
                result.add(temp);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public Nft find(Nft item) {
        return null;
    }

    @Override
    public Nft save(Nft item) {
        return null;
    }

    @Override
    public Nft update(Nft item) {
        return null;
    }

    @Override
    public Nft delete(Nft item) {
        return null;
    }
}
