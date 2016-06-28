package bookstore.service;

import bookstore.DAO.BookDAO;
import bookstore.entity.Book;
import bookstore.entity.BookBrief;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by codeworm on 6/12/16.
 */
@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookDAO bookDAO;

    public long getCount() {
        return bookDAO.getCount();
    }

    public long getCountWithKeyword(String keyword) {
        return bookDAO.getCountWithKeyword(keyword);
    }

    public Book get(int bookid) {
        return bookDAO.get(bookid);
    }

    public List<Book> getList(int offset, int rows) {
        return bookDAO.getList(offset, rows);
    }

    public List<Book> getListWithKeyword(String keyword, int offset, int rows) {
        return bookDAO.getListWithKeyword(keyword, offset, rows);
    }

    private List<BookBrief> summary(List<Book> books) {
        List<BookBrief> items = new ArrayList<BookBrief>();
        for (Book book : books) {
            BookBrief item = new BookBrief();
            item.setId(book.getId());
            item.setCategory(book.getCategory());
            item.setName(book.getName());
            item.setPrice(book.getPrice());
            items.add(item);
        }
        return items;
    }

    public List<BookBrief> getBriefList(int offset, int rows) {
        return summary(bookDAO.getList(offset, rows));
    }

    public List<BookBrief> getBriefListWithKeyword(String keyword, int offset, int rows) {
        return summary(bookDAO.getListWithKeyword(keyword, offset, rows));
    }

    public void save(Book book) {
        bookDAO.save(book);
    }

    public void update(Book book) {
        bookDAO.update(book);
    }

    public void delete(int bookid) {
        bookDAO.delete(bookid);
    }

    public byte[] getCover(int bookid) {
        return bookDAO.getCover(bookid);
    }

    public String saveOrUpdateCover(int bookid, byte[] cover) {
        if (cover == null) {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "upload failed");
            return new Gson().toJson(res);
        } else {
            bookDAO.saveOrUpdateCover(bookid, cover);

            HashMap res = new HashMap<String, Object>();
            res.put("success", true);
            return new Gson().toJson(res);
        }
    }
}
