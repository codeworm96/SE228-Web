package bookstore.book;

import bookstore.DBServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by codeworm on 4/23/16.
 *
 * Add a book
 */
@WebServlet(name = "BookAddServlet", urlPatterns = "/admin/book/add")
public class AddServlet extends DBServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = new Book();
        book.setIsbn(request.getParameter("isbn"));
        book.setName(request.getParameter("name"));
        book.setCategory(request.getParameter("category"));
        book.setCents((int)(Double.parseDouble(request.getParameter("price")) * 100));
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO book(ISBN, name, category, price) VALUES(?,?,?,?);");
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getName());
            stmt.setString(3, book.getCategory());
            stmt.setString(4, String.valueOf(book.getCents()));
            stmt.execute();

            HashMap res = new HashMap<String, Object>();
            res.put("success", true);
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        } catch (SQLException e) {
            e.printStackTrace();

            HashMap res = new HashMap<String, Object>();
            res.put("msg", "Some errors occured.");
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
