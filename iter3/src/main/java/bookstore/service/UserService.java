package bookstore.service;

import bookstore.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by codeworm on 6/12/16.
 */
public interface UserService {
    long getCount();
    User get(String username);
    List<User> getList(int offset, int rows);
    void saveOrUpdate(User user);
    void delete(String username);
    byte[] getAvatar(String username);
    String saveOrUpdateAvatar(String username, byte[] avatar);
}
