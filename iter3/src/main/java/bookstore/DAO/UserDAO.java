package bookstore.DAO;

import bookstore.entity.User;

import java.util.List;

/**
 * Created by codeworm on 6/9/16.
 */
public interface UserDAO {
    long getCount();
    User get(String username);
    List<User> getList(int offset, int rows);
    void saveOrUpdate(User user);
    void delete(String username);
    byte[] getAvatar(String username);
    void saveOrUpdateAvatar(String username, byte[] avatar);
}
