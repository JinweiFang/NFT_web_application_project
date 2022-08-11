package Data.dao;

import Model.Test;

public interface iTestDao extends iDao<Test, Integer> {
    Test find(String name);
}
