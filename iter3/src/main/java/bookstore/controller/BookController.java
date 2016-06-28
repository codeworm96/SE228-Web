package bookstore.controller;

import bookstore.entity.Book;
import bookstore.service.BookService;
import bookstore.utility.FileUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by codeworm on 6/5/16.
 */
@Controller
@RequestMapping("/admin/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/")
    public String index() {
        return "/WEB-INF/view/admin/book.jsp";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(HttpServletRequest request) {
        String tmp;
        int page, rows;
        tmp = request.getParameter("page");
        if (tmp != null) {
            page = Integer.parseInt(tmp);
        } else {
            page = 1;
        }
        tmp = request.getParameter("rows");
        if (tmp != null) {
            rows = Integer.parseInt(tmp);
        } else {
            rows = 10;
        }
        int offset = (page - 1) * rows;

        long cnt = bookService.getCount();
        List<Book> books = bookService.getList(offset, rows);

        HashMap res = new HashMap<String, Object>();
        res.put("total", cnt);
        res.put("rows", books);
        return new Gson().toJson(res);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public String remove(@RequestParam int id) {
        bookService.delete(id);

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return new Gson().toJson(res);
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(@RequestBody String json) {
        Gson gson = new Gson();

        Book book = gson.fromJson(json, Book.class);
        book.setCents((int)(book.getPrice() * 100));
        bookService.save(book);

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return gson.toJson(res);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody String json) {
        Gson gson = new Gson();

        Book book = gson.fromJson(json, Book.class);
        book.setCents((int)(book.getPrice() * 100));
        bookService.update(book);

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return gson.toJson(res);
    }

    @RequestMapping(value = "/cover")
    @ResponseBody
    public String upload_cover(@RequestParam int id, HttpServletRequest request) {
        byte[] content = FileUtil.getUploadContent(request, "cover");
        return bookService.saveOrUpdateCover(id, content);
    }
}
