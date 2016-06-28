package bookstore.DAO;

import bookstore.entity.Book;

import java.util.List;

/**
 * Created by codeworm on 6/12/16.
 */
public interface BookDAO {
    long getCount();
    long getCountWithKeyword(String keyword);
    Book get(int bookid);
    List<Book> getList(int offset, int rows);
    List<Book> getListWithKeyword(String keyword, int offset, int rows);
    void save(Book book);
    void update(Book book);
    void delete(int bookid);
    byte[] getCover(int bookid);
    void saveOrUpdateCover(int bookid, byte[] cover);
}
