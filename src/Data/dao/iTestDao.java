package Data.dao;

import Model.Test;

public interface iTestDao extends iDao<Test> {
    Test find(String name);
}
