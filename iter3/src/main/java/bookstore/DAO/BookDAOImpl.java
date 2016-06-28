package bookstore.DAO;

import bookstore.entity.Book;
import bookstore.utility.HibernateUtil;
import bookstore.utility.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by codeworm on 6/12/16.
 */
@Repository
public class BookDAOImpl implements BookDAO {
    public long getCount() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        long cnt = (Long) session.createQuery("select count(*) from Book").uniqueResult();

        session.getTransaction().commit();

        return cnt;
    }

    public long getCountWithKeyword(String keyword) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        long cnt = (Long) session.createQuery("select count(*) from Book b where b.name like :pattern")
                .setParameter("pattern", '%'+keyword+'%').uniqueResult();

        session.getTransaction().commit();

        return cnt;
    }

    public Book get(int bookid) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Book book = session.get(Book.class, bookid);

        session.getTransaction().commit();

        return book;
    }

    public List<Book> getList(int offset, int rows) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Book> res = session.createQuery("select b from Book b")
                .setFirstResult(offset)
                .setMaxResults(rows)
                .list();

        session.getTransaction().commit();

        return res;
    }

    public List<Book> getListWithKeyword(String keyword, int offset, int rows) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Book> res = session.createQuery("select b from Book b where b.name like :pattern")
                .setParameter("pattern", '%'+keyword+'%')
                .setFirstResult(offset)
                .setMaxResults(rows)
                .list();

        session.getTransaction().commit();

        return res;
    }

    public void save(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.save(book);

        session.getTransaction().commit();
    }

    public void update(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.update(book);

        session.getTransaction().commit();
    }

    public void delete(int bookid) {
        // delete in mysql
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Book book = session.load(Book.class, bookid);
        session.delete(book);

        session.getTransaction().commit();

        // also delete cover in mongodb
        MongoCollection<Document> collection = MongoUtil.getDB().getCollection("book");
        collection.deleteMany(eq("bookid", bookid));
    }

    public byte[] getCover(int bookid) {
        MongoDatabase db = MongoUtil.getDB();
        MongoCollection<Document> collection = db.getCollection("book");
        Document doc = collection.find(eq("bookid", bookid)).first();
        if (doc == null || doc.get("cover") == null) {
            return null;
        } else {
            Binary binary = (Binary) doc.get("cover");
            return binary.getData();
        }
    }

    public void saveOrUpdateCover(int bookid, byte[] cover) {
        if (cover == null) {
            return;
        }

        Binary img = new Binary(cover);

        MongoCollection<Document> collection = MongoUtil.getDB().getCollection("book");
        Document doc = collection.find(eq("bookid", bookid)).first();
        if (doc == null) {
            doc = new Document();
            doc.append("bookid", bookid).append("cover", img);
            collection.insertOne(doc);
        } else {
            collection.updateOne(eq("bookid", bookid), set("cover", img));
        }
    }
}
