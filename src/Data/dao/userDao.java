package Data.dao;

import Domain.User;

public interface userDao extends AbstractDao<User> {
    User findByUsername(User item);
    User updateByUsername(User item);
}
