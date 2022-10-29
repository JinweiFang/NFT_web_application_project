package Data.dao.impl;

import Data.abstractConnect;
import Data.dao.nftDao;
import Domain.NFT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class nftContext extends abstractConnect implements nftDao {

    public nftContext(Connection conn) {
        super(conn);
    }
    public Collection<NFT> findAll() { return null; }
    public NFT find(NFT nft) { return null; }
    public NFT save(NFT nft) { return null; }
    public NFT update(NFT nft) { return null; }
    public NFT delete(NFT nft) { return null; }

    public double getNftPrice(int id){
        String sql = "SELECT price FROM nfts WHERE id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet res = pstm.executeQuery();

            if (res.next()) {
                return res.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
