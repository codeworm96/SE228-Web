package bookstore.service;

import bookstore.DAO.UserDAO;
import bookstore.entity.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by codeworm on 6/12/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Qualifier("userDAOimpl")
    @Autowired
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public long getCount() {
        return userDAO.getCount();
    }

    public User get(String username) {
        return userDAO.get(username);
    }

    public List<User> getList(int offset, int rows) {
        return userDAO.getList(offset, rows);
    }

    public void saveOrUpdate(User user) {
        userDAO.saveOrUpdate(user);
    }

    public void delete(String username) {
        userDAO.delete(username);
    }

    public byte[] getAvatar(String username) {
        return userDAO.getAvatar(username);
    }

    public String saveOrUpdateAvatar(String username, byte[] avatar) {
        if (avatar == null) {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "upload failed");
            return new Gson().toJson(res);
        } else {
            userDAO.saveOrUpdateAvatar(username, avatar);

            HashMap res = new HashMap<String, Object>();
            res.put("success", true);
            return new Gson().toJson(res);
        }
    }
}
