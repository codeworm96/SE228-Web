package bookstore.controller;

import bookstore.entity.Book;
import bookstore.entity.BookBrief;
import bookstore.service.BookService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by codeworm on 6/3/16.
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public String list(@RequestParam(required = false) String q, @RequestParam int id, @RequestParam int size) {
        if (q == null) {
            q = "";
        }

        int offset = (id - 1) * size;

        long cnt;
        List<BookBrief> books;
        if (q.equals("")) {
            cnt = bookService.getCount();
            books = bookService.getBriefList(offset, size);
        } else {
            cnt = bookService.getCountWithKeyword(q);
            books = bookService.getBriefListWithKeyword(q, offset, size);
        }

        Map res = new HashMap();
        res.put("incomplete", offset + books.size() < cnt);
        res.put("items", books);

        return new Gson().toJson(res);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public String detail(@RequestParam int id) {
        Book book = bookService.get(id);

        return new Gson().toJson(book);
    }
}
