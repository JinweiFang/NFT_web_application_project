package Data.dao;

import Domain.Nft;

import java.util.Collection;

public interface NftDao extends AbstractDao<Nft> {
    Collection<Nft> findNftsByOwnerID(Nft nft);
}