package bookstore.DAO;

import bookstore.entity.User;
import bookstore.utility.HibernateUtil;
import bookstore.utility.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by codeworm on 6/9/16.
 */
@Repository
public class UserDAOimpl implements UserDAO {

    private void loadMongodbInfo(User user) {
        if (user == null) {
            return;
        }

        MongoCollection<Document> collection = MongoUtil.getDB().getCollection("user");
        Document doc = collection.find(eq("username", user.getUsername())).first();
        if (doc != null) {
            user.setEmail(doc.getString("email"));
            user.setPhone(doc.getString("phone"));
            user.setAddress(doc.getString("address"));
        }

        if (user.getEmail() == null) {
            user.setEmail("");
        }
        if (user.getPhone() == null) {
            user.setPhone("");
        }
        if (user.getAddress() == null) {
            user.setAddress("");
        }
    }

    public long getCount() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        long res = (Long) session.createQuery("select count(*) from User").uniqueResult();

        session.getTransaction().commit();

        return res;
    }

    public User get(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        User res = session.get(User.class, username);

        session.getTransaction().commit();

        loadMongodbInfo(res);

        return res;
    }

    public List<User> getList(int offset, int rows) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<User> users = session.createQuery("select u from User u")
                .setFirstResult(offset)
                .setMaxResults(rows)
                .list();

        session.getTransaction().commit();

        for (User u : users) {
            loadMongodbInfo(u);
        }

        return users;
    }

    public void saveOrUpdate(User user) {
        if (user == null) {
            return;
        }

        // in mysql
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.saveOrUpdate(user);

        session.getTransaction().commit();

        //in mongodb
        MongoCollection<Document> collection = MongoUtil.getDB().getCollection("user");
        Document doc = collection.find(eq("username", user.getUsername())).first();
        if (doc == null) {
            // save
            doc = new Document("username", user.getUsername());
            if (user.getEmail() != null) {
                doc.append("email", user.getEmail());
            }
            if (user.getPhone() != null) {
                doc.append("phone", user.getPhone());
            }
            if (user.getAddress() != null) {
                doc.append("address", user.getAddress());
            }

            collection.insertOne(doc);
        } else {
            // update
            List updates = new ArrayList();
            if (user.getEmail() != null) {
                updates.add(set("email", user.getEmail()));
            }
            if (user.getPhone() != null) {
                updates.add(set("phone", user.getPhone()));
            }
            if (user.getAddress() != null) {
                updates.add(set("address", user.getAddress()));
            }

            collection.updateOne(eq("username", user.getUsername()), combine(updates));
        }
    }

    public void delete(String username) {
        // delete in mysql
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        User user = session.load(User.class, username);
        session.delete(user);

        session.getTransaction().commit();

        // delete in mongodb
        MongoCollection<Document> collection = MongoUtil.getDB().getCollection("user");
        collection.deleteMany(eq("username", username));
    }

    public byte[] getAvatar(String username) {
        MongoDatabase db = MongoUtil.getDB();
        MongoCollection<Document> collection = db.getCollection("user");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null || doc.get("avatar") == null) {
            return null;
        } else {
            Binary binary = (Binary) doc.get("avatar");
            return binary.getData();
        }
    }

    public void saveOrUpdateAvatar(String username, byte[] avatar) {
        if (avatar == null) {
            return;
        }

        Binary img = new Binary(avatar);

        MongoCollection<Document> collection = MongoUtil.getDB().getCollection("user");
        Document doc = collection.find(eq("username", username)).first();
        if (doc == null) {
            doc = new Document();
            doc.append("username", username).append("avatar", img);
            collection.insertOne(doc);
        } else {
            collection.updateOne(eq("username", username), set("avatar", img));
        }
    }
}
