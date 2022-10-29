package Data.dao;

import Domain.NFT;

public interface nftDao extends AbstractDao<NFT>{
    double getNftPrice(int id);
}
