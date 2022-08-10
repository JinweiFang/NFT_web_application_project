package Data.Repo;

import Data.Repo.iRepo;
import Model.Test;

public interface iTestRepo extends iRepo<Test, Integer> {
    Test find(String name);
}
