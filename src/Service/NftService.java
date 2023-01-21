package Service;

import Data.dao.NftDao;
import Data.dao.impl.nftContext;
import Data.dao.impl.userContext;
import Data.dao.userDao;
import Data.dataSource;
import Domain.Nft;

import java.util.List;

import static Utils.DateUtils.generateUnixTimestamp;

public class NftService {
    private final NftDao nftRepo;

    public NftService() {
        dataSource ds = new dataSource();
        this.nftRepo = new nftContext(ds.getConn());
    }

    public List<Nft> getUserNFts(int ownerId) {
        Nft nft = new Nft();
        nft.setOwnerId(ownerId);

        return (List<Nft>) nftRepo.findNftsByOwnerID(nft);
    }
}
