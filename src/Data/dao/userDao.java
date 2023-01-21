package Data.dao;

import Domain.User;

public interface userDao extends AbstractDao<User> {
    User findByUsername(User item);
    User updatePasswordByUsername(User item);
    User updatePersonalInfo(User item);
    User updateProfileImage(User item);
    String getSecurityAnswer(String username, int status);
}
