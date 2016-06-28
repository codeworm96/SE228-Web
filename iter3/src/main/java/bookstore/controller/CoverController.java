package bookstore.controller;

import bookstore.service.BookService;
import bookstore.utility.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by codeworm on 6/4/16.
 *
 * To show a book's cover
 */
@Controller
public class CoverController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/cover")
    public void cover(@RequestParam int id, HttpServletRequest request, HttpServletResponse response) {
        byte[] buf = bookService.getCover(id);
        if (buf == null) {
            buf = FileUtil.loadFile(request, "img/default_cover.png");
        }

        response.setContentType("image/png");
        try {
            OutputStream out = response.getOutputStream();
            out.write(buf);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}