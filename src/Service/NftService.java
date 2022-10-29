package Service;

import Data.dao.impl.nftContext;
import Data.dao.nftDao;
import Data.dataSource;

public class NftService {

    private nftDao nftRepo;

    public NftService() {
        dataSource ds = new dataSource();
        this.nftRepo = new nftContext(ds.getConn());
    }

    public double getNftPrice(int id){
        return nftRepo.getNftPrice(id);
    }

}
